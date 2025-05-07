package com.grittonbelldev.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

/**
 * CORS filter for enabling cross-origin requests.
 *
 * <p>This filter inspects the incoming request's Origin header and, if the origin
 * is in the approved list, sets the appropriate CORS response headers to allow
 * the browser to make the request. It supports credentials and multiple HTTP methods.</p>
 *
 * <p>Applies to all JAX-RS resources via the {@code @Provider} annotation.</p>
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    private static final Logger logger = LogManager.getLogger(CorsFilter.class);

    /**
     * A list of allowed origins that are permitted to access the API.
     */
    private static final List<String> allowedOrigins = List.of(
            "http://localhost:4200",         // Local development server
            "https://jgrittonbell.github.io" // Deployed frontend (GitHub Pages)
    );

    /**
     * Applies CORS headers to every response.
     *
     * <p>If the request's Origin is in the list of allowed origins, the response
     * will include an {@code Access-Control-Allow-Origin} header with the matched origin.</p>
     *
     * <p>It also adds standard CORS headers for methods, headers, credentials, and caching.</p>
     *
     * @param requestContext  the context of the incoming request
     * @param responseContext the context of the outgoing response
     * @throws IOException never thrown in this implementation
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString("Origin");

        // Only allow specific origins defined in the whitelist
        if (origin != null && allowedOrigins.contains(origin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        }

        // These headers apply regardless of origin
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600"); // 14 days
    }
}
