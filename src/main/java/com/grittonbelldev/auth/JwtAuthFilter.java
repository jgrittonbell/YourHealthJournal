package com.grittonbelldev.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.service.UserService;
import com.grittonbelldev.util.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * JAX-RS container request filter that handles authentication via AWS Cognito JWT.
 *
 * This filter performs the following responsibilities:
 * <ul>
 *   <li>Extracts and verifies the JWT token from the Authorization header.</li>
 *   <li>Validates the token using the project's JWKS logic in {@link JwtUtils}.</li>
 *   <li>Retrieves or creates a {@link User} entity matching the Cognito subject and email.</li>
 *   <li>Replaces the request's {@link SecurityContext} so that the user principal reflects the internal User ID.</li>
 * </ul>
 * This enables secure API access with per-user identification while preserving compatibility
 * with JAX-RS-based role checks and principal lookups.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final UserService userService = new UserService();
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Main filter method invoked for each incoming request.
     * Validates the JWT and sets up user identity context.
     *
     * @param ctx the container request context
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String method = ctx.getMethod();
        String path = ctx.getUriInfo().getPath();
        logger.debug("Request method: {}, path: {}", method, path);

        // Skip JWT processing for preflight CORS requests
        if ("OPTIONS".equalsIgnoreCase(method)) {
            logger.debug("Skipping JWT filter for OPTIONS preflight");
            return;
        }

        // Allow unauthenticated access to the token endpoint
        if (path.equals("auth/token") || path.equals("/auth/token")) {
            logger.debug("Skipping JWT filter for public path: {}", path);
            return;
        }

        // Read and validate the Authorization header
        String auth = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith(BEARER_PREFIX)) {
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Remove the prefix and trim whitespace
        String token = auth.substring(BEARER_PREFIX.length()).trim();

        // Validate and decode the JWT token
        DecodedJWT jwt;
        try {
            jwt = JwtUtils.validate(token);
        } catch (Exception e) {
            logger.warn("JWT validation failed", e);
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Extract necessary claims from the JWT
        String cognitoSub = jwt.getSubject();
        String email = jwt.getClaim("email").asString();
        if (cognitoSub == null || email == null) {
            logger.warn("Missing sub or email in token");
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Retrieve or create an internal User record based on Cognito sub and email
        User user = userService.findOrCreateByCognitoId(cognitoSub, email);
        final long internalId = user.getId();

        // Replace the SecurityContext with one that exposes the internal User ID
        final SecurityContext original = ctx.getSecurityContext();
        ctx.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> Long.toString(internalId);
            }

            @Override
            public boolean isUserInRole(String role) {
                return original.isUserInRole(role);
            }

            @Override
            public boolean isSecure() {
                return original.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }
}
