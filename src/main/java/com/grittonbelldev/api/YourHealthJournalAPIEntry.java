package com.grittonbelldev.api;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Entry point for the JAX-RS application.
 * <p>
 * This class configures the base API path, registers resource classes,
 * and enables JSON serialization via Jackson.
 * </p>
 */
@ApplicationPath("/api")
public class YourHealthJournalAPIEntry extends Application {

    /**
     * Returns a set of classes to be registered with the JAX-RS runtime.
     * <p>
     * Includes all resource endpoint classes and any additional features
     * such as JSON support.
     * </p>
     *
     * @return set of configured JAX-RS classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                MealResource.class,      // REST endpoints for meals
                GlucoseResource.class,   // REST endpoints for glucose readings
                FavoriteResource.class,  // REST endpoints for favorites
                FoodSearchResource.class,// REST endpoints for nutritionix API
                JacksonFeature.class     // Enables JSON (de)serialization via Jackson
        );
    }
}