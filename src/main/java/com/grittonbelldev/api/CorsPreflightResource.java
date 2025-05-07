package com.grittonbelldev.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * JAX-RS resource for handling CORS preflight (OPTIONS) requests.
 *
 * This resource enables browsers to validate whether the application
 * supports cross-origin HTTP requests. It responds with appropriate
 * CORS headers based on the request's origin.
 *
 * Path is matched with a wildcard to ensure OPTIONS requests to any endpoint
 * are intercepted and handled here.
 */
@Path("{any: .*}")
public class CorsPreflightResource {

    // List of origins explicitly allowed to make cross-origin requests
    private static final List<String> allowedOrigins = List.of(
            "http://localhost:4200",             // Angular dev server
            "https://jgrittonbell.github.io"     // GitHub Pages deployment
    );

    /**
     * Responds to HTTP OPTIONS requests for CORS preflight negotiation.
     *
     * Adds appropriate headers to indicate allowed methods, headers, and
     * credentials support for approved origins.
     *
     * @param headers incoming request headers, used to extract the Origin
     * @return HTTP 200 response with appropriate CORS headers
     */
    @OPTIONS
    public Response handlePreflight(@Context HttpHeaders headers) {
        // Determine the origin from the incoming request
        String origin = headers.getRequestHeader("Origin") != null && !headers.getRequestHeader("Origin").isEmpty()
                ? headers.getRequestHeader("Origin").get(0)
                : "";

        Response.ResponseBuilder response = Response.ok();

        // If the origin is allowed, add CORS headers for that origin
        if (allowedOrigins.contains(origin)) {
            response
                    .header("Access-Control-Allow-Origin", origin)
                    .header("Access-Control-Allow-Credentials", "true");
        }

        // Standard CORS headers for all responses
        return response
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Max-Age", "1209600") // 14 days
                .build();
    }
}
