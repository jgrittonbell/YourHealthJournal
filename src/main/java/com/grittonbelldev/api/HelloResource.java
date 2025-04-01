package com.grittonbelldev.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloResource {


    private final Logger logger = LogManager.getLogger(this.getClass());


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        logger.info("Hello World");
        return Response.status(200).entity("Hello from Jersey!").build();
    }
}
