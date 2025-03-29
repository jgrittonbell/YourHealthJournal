package com.grittonbelldev.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grittonbelldev.auth.*;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;
import org.apache.commons.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;


@WebServlet(
        urlPatterns = {"/auth"}
)

/**
 * Inspired by: https://stackoverflow.com/questions/52144721/how-to-get-access-token-using-client-credentials-using-java-code
 */

public class Auth extends HttpServlet {
    String CLIENT_ID;
    String CLIENT_SECRET;
    String OAUTH_URL;
    String LOGIN_URL;
    String REDIRECT_URL;
    String REGION;
    String POOL_ID;
    Keys jwks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        CLIENT_ID = (String) context.getAttribute("client.id");
        CLIENT_SECRET = (String) context.getAttribute("client.secret");
        OAUTH_URL = (String) context.getAttribute("oauthURL");
        LOGIN_URL = (String) context.getAttribute("loginURL");
        REDIRECT_URL = (String) context.getAttribute("redirectURL");
        REGION = (String) context.getAttribute("region");
        POOL_ID = (String) context.getAttribute("poolId");
        loadKey();
    }

    /**
     * Handles the redirect from Cognito with an authorization code.
     * Exchanges the code for tokens, verifies the ID token, and determines whether the user
     * exists in the application's database. If the user is new, forwards to a registration flow.
     *
     * @param req  HTTP request containing Cognito's auth code
     * @param resp HTTP response used for redirection or rendering views
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if a network or IO error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authCode = req.getParameter("code");

        // If no authorization code is present, something went wrong in the Cognito login
        if (authCode == null) {
            req.setAttribute("errorMessage", "Invalid login attempt. Please try again.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        try {
            // Step 1: Exchange the auth code for tokens
            HttpRequest authRequest = buildAuthRequest(authCode);
            TokenResponse tokenResponse = getToken(authRequest);

            // Step 2: Verify and decode the ID token
            DecodedJWT jwt = validate(tokenResponse);

            // Step 3: Extract user details from the verified token
            String cognitoId = jwt.getClaim("sub").asString(); // Unique ID from Cognito
            String email = jwt.getClaim("email").asString();

            // Step 4: Look up the user in the database by Cognito ID
            GenericDAO<User> userDao = new GenericDAO<>(User.class);
            User user = userDao.getById(cognitoId);

            HttpSession session = req.getSession(true);
            session.setAttribute("cognitoId", cognitoId); // Store Cognito ID for later use
            session.setAttribute("email", email);

            if (user != null) {
                // Existing user found — store in session and forward to home page
                session.setAttribute("user", user);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } else {
                // No user found — forward to registration servlet to collect details
                req.getRequestDispatcher("/registerUser").forward(req, resp);
            }

        } catch (IOException | InterruptedException e) {
            logger.error("Error during authentication: " + e.getMessage(), e);
            req.setAttribute("errorMessage", "Authentication failed. Please try again.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    /**
     * Sends the request for a token to Cognito and maps the response
     * @param authRequest the request to the oauth2/token url in cognito
     * @return response from the oauth2/token endpoint which should include id token, access token and refresh token
     * @throws IOException
     * @throws InterruptedException
     */
    private TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response = null;

        response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());


        logger.debug("Response headers: " + response.headers().toString());
        logger.debug("Response body: " + response.body().toString());

        ObjectMapper mapper = new ObjectMapper();
        TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
        logger.debug("Id token: " + tokenResponse.getIdToken());

        return tokenResponse;

    }

    /**
     * Verifies the ID token received from Cognito, checks its signature,
     * and returns the decoded JWT so user details can be extracted by the caller.
     *
     * @param tokenResponse The response from Cognito's /oauth2/token endpoint
     * @return Decoded and verified JWT containing user claims
     * @throws IOException If token cannot be verified or public key cannot be generated
     */
    private DecodedJWT validate(TokenResponse tokenResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Parse the JWT header to extract the key ID (kid)
        CognitoTokenHeader tokenHeader = mapper.readValue(
                CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(),
                CognitoTokenHeader.class
        );
        String keyId = tokenHeader.getKid();

        // Find the matching key in the JWKS using the key ID (kid)
        KeysItem matchingKey = jwks.getKeys().stream()
                .filter(key -> key.getKid().equals(keyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No matching key found in JWKS for kid: " + keyId));

        // Decode the modulus (n) and exponent (e) from Base64 to BigInteger
        BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(matchingKey.getN()));
        BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(matchingKey.getE()));

        // Generate RSA public key from the decoded values
        PublicKey publicKey;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error("Error generating public key for JWT validation", e);
            throw new IOException("Public key generation failed", e);
        }

        // Build a verifier to ensure the token is:
        // - signed using RSA
        // - issued by the correct Cognito User Pool
        // - an ID token (not access or refresh)
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
        String issuer = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .withClaim("token_use", "id")
                .build();

        // Perform the verification (throws exception if invalid)
        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());

        // Log useful values for debugging
        logger.debug("here's the username: " + jwt.getClaim("cognito:username").asString());
        logger.debug("here are all the available claims: " + jwt.getClaims());

        return jwt;
    }

    /** Create the auth url and use it to build the request.
     *
     * @param authCode auth code received from Cognito as part of the login process
     * @return the constructed oauth request
     */
    private HttpRequest buildAuthRequest(String authCode) {
        String keys = CLIENT_ID + ":" + CLIENT_SECRET;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client-secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        return request;
    }

    /**
     * Gets the JSON Web Key Set (JWKS) for the user pool from cognito and loads it
     * into objects for easier use.
     *
     * JSON Web Key Set (JWKS) location: https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
     * Demo url: https://cognito-idp.us-east-2.amazonaws.com/us-east-2_XaRYHsmKB/.well-known/jwks.json
     *
     * @see Keys
     * @see KeysItem
     */
    private void loadKey() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("Keys are loaded. Here's e: " + jwks.getKeys().get(0).getE());
        } catch (IOException ioException) {
            logger.error("Cannot load json..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading json" + e.getMessage(), e);
        }
    }

}

