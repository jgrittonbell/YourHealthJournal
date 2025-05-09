package com.grittonbelldev.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.grittonbelldev.auth.CognitoJWTParser;
import com.grittonbelldev.auth.Keys;
import com.grittonbelldev.auth.KeysItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

/**
 * Utility class for validating Cognito JWTs and extracting claims.
 *
 * <p>This class is responsible for loading Cognito configuration, retrieving and caching
 * JSON Web Keys (JWKs), verifying JWT signatures, and validating claims such as
 * expiration and issuer.</p>
 *
 * <p>The class is used to support secure authentication flows where JWTs are issued
 * by AWS Cognito and verified within this application.</p>
 */
public class JwtUtils implements PropertiesLoaderProd {
    private static final Logger logger = LogManager.getLogger(JwtUtils.class);

    // Cognito configuration values loaded from properties
    private static final String REGION;
    private static final String POOL_ID;
    private static final String ISSUER;
    private static final String JWKS_URL;

    // In-memory cache of keys fetched from Cognito
    private static Keys cachedKeys;
    private static long lastFetch;
    private static final long CACHE_TTL_MS = 60 * 60 * 1000L; // 1 hour

    // Static initializer to load properties and construct Cognito URLs
    static {
        Properties props;
        try {
            props = new PropertiesLoaderProd() {}.loadProperties("/cognito.properties");
        } catch (Exception e) {
            logger.error("Failed to load cognito.properties", e);
            throw new ExceptionInInitializerError(
                    "Unable to load cognito.properties: " + e.getMessage()
            );
        }

        REGION  = props.getProperty("region");
        POOL_ID = props.getProperty("poolId");
        if (REGION == null || POOL_ID == null) {
            throw new ExceptionInInitializerError(
                    "cognito.properties must define region and poolId"
            );
        }

        // Construct issuer and JWKS URL for validation
        ISSUER  = "https://cognito-idp." + REGION + ".amazonaws.com/" + POOL_ID;
        JWKS_URL = ISSUER + "/.well-known/jwks.json";
    }

    // Private constructor to prevent instantiation
    private JwtUtils() {}

    /**
     * Fetches and caches the public keys (JWKs) used by Cognito to sign JWTs.
     *
     * @return a Keys object containing the current JWKs
     */
    private static synchronized Keys fetchJwks() {
        long now = System.currentTimeMillis();
        if (cachedKeys == null || now - lastFetch > CACHE_TTL_MS) {
            // Use JAX-RS client to retrieve the JWKS JSON from Cognito
            Client client = ClientBuilder.newClient();
            cachedKeys = client
                    .target(URI.create(JWKS_URL))
                    .request(MediaType.APPLICATION_JSON)
                    .get(Keys.class);
            lastFetch = now;
        }
        return cachedKeys;
    }

    /**
     * Validates a JWT's format, signature, expiration, and issuer, and returns the subject claim.
     *
     * @param jwt the raw JWT string from the Authorization header
     * @return the value of the "sub" claim
     * @throws ProcessingException if the JWT is invalid for any reason
     */
    public static String validateAndGetSubject(String jwt) {
        // Parse and validate basic format and extract headers
        CognitoJWTParser.validateJWT(jwt);
        var headerJson = CognitoJWTParser.getHeader(jwt);

        String kid = headerJson.getString("kid");
        String alg = headerJson.getString("alg");
        if (!"RS256".equals(alg)) {
            logger.info("Invalid algorithm: {}", alg);
            throw new ProcessingException("Unexpected alg: " + alg);

        }

        // Locate the correct key from the JWK set using the key ID (kid)
        List<KeysItem> keys = fetchJwks().getKeys();
        KeysItem match = keys.stream()
                .filter(k -> kid.equals(k.getKid()))
                .findFirst()
                .orElseThrow(() ->
                        new ProcessingException("No JWK found for kid=" + kid)
                );

        try {
            // Decode modulus (n) and exponent (e) to reconstruct RSA public key
            BigInteger n = new BigInteger(1,
                    Base64.getUrlDecoder().decode(match.getN()));
            BigInteger e = new BigInteger(1,
                    Base64.getUrlDecoder().decode(match.getE()));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pub = kf.generatePublic(new RSAPublicKeySpec(n, e));

            // Use RSA key to verify the JWT signature
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(pub);
            String[] parts = jwt.split("\\.");
            sig.update((parts[0] + "." + parts[1])
                    .getBytes(StandardCharsets.US_ASCII));
            if (!sig.verify(Base64.getUrlDecoder().decode(parts[2]))) {
                logger.info("Invalid signature: {}", parts[2]);
                throw new ProcessingException("Invalid JWT signature");
            }

            // Decode payload and check expiration and issuer
            var payload = CognitoJWTParser.getPayload(jwt);
            long expMs = payload.getLong("exp") * 1000L;
            if (System.currentTimeMillis() > expMs) {
                logger.info("JWT expired: {}", expMs);
                throw new ProcessingException("JWT expired");
            }

            String tokenIssuer = payload.getString("iss");
            if (!ISSUER.equals(tokenIssuer)) {
                logger.info("Invalid issuer: {}", tokenIssuer);
                throw new ProcessingException("Invalid issuer: " + tokenIssuer);
            }

            // Return the subject claim ("sub") from the token
            return payload.getString("sub");

        } catch (ProcessingException pe) {
            logger.info("JWT not valid: {}", pe.getMessage());
            throw pe;
        } catch (Exception ex) {
            logger.info("JWT not valid: {}", ex.getMessage());
            throw new ProcessingException("JWT validation error", ex);
        }
    }

    /**
     * Validates a JWT and returns a decoded representation of its claims.
     *
     * @param token the raw JWT string
     * @return a DecodedJWT object containing claims such as email and sub
     * @throws ProcessingException if validation fails
     */
    public static DecodedJWT validate(String token) {
        // Perform full validation first
        validateAndGetSubject(token);
        // Return decoded token for claim access
        return JWT.decode(token);
    }
}
