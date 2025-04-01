package com.grittonbelldev.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

@ApplicationPath("/api")
public class ApiConfig extends Application {

    private final Logger logger = LogManager.getLogger(this.getClass());
    public ApiConfig() {
        logger.info(">>> Jersey ApiConfig initialized.");
    }
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(HelloResource.class );
        return h;
    }
}
