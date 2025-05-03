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
 * JAX-RS filter that:
 * 1) reads and validates the Cognito JWT from the
 *    Authorization: Bearer <token> header,
 * 2) looks up or creates an internal User by (cognito_id, email),
 * 3) replaces the SecurityContext so getUserPrincipal().getName() == internal user.id.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final UserService userService = new UserService();
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        // DEV-ONLY BYPASS: any Host containing "localhost"
        /*
        String host = ctx.getUriInfo().getRequestUri().getHost();
        logger.info("JwtAuthFilter: request host = {}", host);
        if ("localhost".equals(host) || "127.0.0.1".equals(host)) {
            SecurityContext orig = ctx.getSecurityContext();
            ctx.setSecurityContext(new SecurityContext() {
                @Override public Principal getUserPrincipal()      { return () -> "1"; }
                @Override public boolean   isUserInRole(String r)   { return true; }
                @Override public boolean   isSecure()               { return orig.isSecure(); }
                @Override public String    getAuthenticationScheme(){ return "DEV";  }
            });
            return;
        }
        */
        // ──────────────────────────────────────────────────

        // Skip filtering for public paths like /api/auth/token
        String path = ctx.getUriInfo().getPath();
        logger.debug("Request path: {}", path);

        if (path.equals("auth/token") || path.equals("/auth/token")) {
            logger.debug("Skipping JWT filter for public path: {}", path);
            return;
        }

        // 1) Extract and validate Authorization header
        String auth = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith(BEARER_PREFIX)) {
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        String token = auth.substring(BEARER_PREFIX.length()).trim();

        // 2) Validate JWT to get full decoded token
        DecodedJWT jwt;
        try {
            jwt = JwtUtils.validate(token);
        } catch (Exception e) {
            logger.warn("JWT validation failed", e);
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // 3) Pull out subject ("sub") and email claim
        String cognitoSub = jwt.getSubject();
        String email      = jwt.getClaim("email").asString();
        if (cognitoSub == null || email == null) {
            logger.warn("Missing sub or email in token");
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // 4) Map (cognitoSub, email) → internal User record
        User user = userService.findOrCreateByCognitoId(cognitoSub, email);
        final long internalId = user.getId();

        // 5) Wrap the SecurityContext so getUserPrincipal() returns internalId
        final SecurityContext original = ctx.getSecurityContext();
        ctx.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> Long.toString(internalId);
            }
            @Override public boolean isUserInRole(String role) {
                return original.isUserInRole(role);
            }
            @Override public boolean isSecure() {
                return original.isSecure();
            }
            @Override public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }
}
