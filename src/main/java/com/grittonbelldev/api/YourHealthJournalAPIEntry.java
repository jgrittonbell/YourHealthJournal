package com.grittonbelldev.api;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api")
public class YourHealthJournalAPIEntry extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                MealResource.class,
                GlucoseResource.class,
                FavoriteResource.class,
                JacksonFeature.class
        );
    }
}