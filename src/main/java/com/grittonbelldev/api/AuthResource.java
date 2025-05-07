package com.grittonbelldev.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grittonbelldev.dto.AuthCodeRequest;
import com.grittonbelldev.dto.AuthTokenResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * JAX-RS resource responsible for handling OAuth2 token exchange using AWS Cognito.
 *
 * This class defines a single endpoint that receives an authorization code,
 * exchanges it with Cognito for an access token and ID token, and returns
 * the response payload in a simplified DTO format.
 *
 * ServletContext is used to pull preloaded secrets such as the client ID,
 * client secret, redirect URL, and Cognito OAuth URL.
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final Logger logger = LogManager.getLogger(this.getClass());

    // ServletContext provides access to application-scoped configuration and secrets
    @Context
    private ServletContext context;

    /**
     * POST /api/auth/token
     *
     * Accepts an authorization code and performs the OAuth2 token exchange
     * with Cognito to retrieve an ID token and access token.
     *
     * @param request JSON payload containing the authorization code
     * @return Response containing a JWT and expiration or an appropriate error
     */
    @POST
    @Path("/token")
    public Response exchangeCodeForToken(AuthCodeRequest request) {
        String code = request.getCode();
        logger.info("Starting token exchange for code: {}", code);

        try {
            // Retrieve required config values from the servlet context
            String clientId     = (String) context.getAttribute("client.id");
            String clientSecret = (String) context.getAttribute("client.secret");
            String redirectUri  = (String) context.getAttribute("redirectURL");
            String oauthURL     = (String) context.getAttribute("oauthURL");

            // Validate that all required config values are present
            if (clientId == null || clientSecret == null || redirectUri == null || oauthURL == null) {
                logger.error("Missing Cognito configuration in servlet context. " +
                                "clientId={}, clientSecretPresent={}, redirectUri={}, oauthURL={}",
                        clientId, clientSecret != null, redirectUri, oauthURL);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Missing Cognito configuration in servlet context").build();
            }

            // Build the form-encoded request body for the token request
            String form = "grant_type=authorization_code"
                    + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")
                    + "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8")
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
                    + "&code=" + URLEncoder.encode(code, "UTF-8");

            logger.debug("Submitting token exchange request to Cognito: oauthURL={}, redirectUri={}", oauthURL, redirectUri);

            // Construct the HTTP request to Cognito's /token endpoint
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(oauthURL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            // Send the request and capture the response
            HttpResponse<String> response = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            logger.info("Received response from Cognito: statusCode={}, body={}",
                    response.statusCode(), response.body());

            // If token exchange failed, return 401 Unauthorized
            if (response.statusCode() != 200) {
                logger.warn("Token exchange failed. Response code: {}", response.statusCode());
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Failed to exchange code with Cognito").build();
            }

            // Parse JSON response to extract tokens and expiration
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.body());

            AuthTokenResponse tokenResponse = new AuthTokenResponse(
                    json.get("id_token").asText(),
                    json.get("access_token").asText(),
                    json.get("expires_in").asInt()
            );

            logger.info("Token exchange successful. Returning token payload.");
            return Response.ok(tokenResponse).build();

        } catch (IOException | InterruptedException e) {
            logger.error("Exception during token exchange", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Exception during token exchange").build();
        }
    }
}
