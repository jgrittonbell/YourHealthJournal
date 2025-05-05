package com.grittonbelldev.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Handles CORS preflight (OPTIONS) requests and adds the appropriate CORS headers.
 */
@Path("{any: .*}")
public class CorsPreflightResource {

    private static final List<String> allowedOrigins = List.of(
            "http://localhost:4200",
            "https://jgrittonbell.github.io"
    );

    @OPTIONS
    public Response handlePreflight(@Context HttpHeaders headers) {
        String origin = headers.getRequestHeader("Origin") != null && !headers.getRequestHeader("Origin").isEmpty()
                ? headers.getRequestHeader("Origin").get(0)
                : "";

        Response.ResponseBuilder response = Response.ok();

        if (allowedOrigins.contains(origin)) {
            response
                    .header("Access-Control-Allow-Origin", origin)
                    .header("Access-Control-Allow-Credentials", "true");
        }

        return response
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }
}
