package com.grittonbelldev.api;

import com.grittonbelldev.auth.JwtAuthFilter;
import com.grittonbelldev.filter.CorsFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Entry point for the JAX-RS application.
 *
 * This class extends {@link Application} to configure all REST resources,
 * filters, and features used by the application.
 *
 * The {@code @ApplicationPath("/api")} annotation defines the base URI
 * for all JAX-RS resources. All registered endpoints will be available
 * under the "/api" path.
 */
@ApplicationPath("/api")
public class YourHealthJournalAPIEntry extends Application {

    /**
     * Registers all classes needed by the JAX-RS runtime.
     *
     * This method returns a {@link Set} of classes that includes:
     * <ul>
     *   <li>All REST resource classes that define the API endpoints</li>
     *   <li>Filters for authentication and CORS support</li>
     *   <li>Jackson modules for JSON support and Java time serialization</li>
     * </ul>
     *
     * @return set of classes to be loaded by the JAX-RS container
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                MealResource.class,          // Provides CRUD operations for meals
                GlucoseResource.class,       // Provides CRUD operations for glucose readings
                FavoriteResource.class,      // Provides endpoints for managing favorites
                UserResource.class,          // Provides endpoints for the authenticated user's profile
                NutritionixResource.class,   // Provides integration with the Nutritionix API
                AuthResource.class,          // Handles OAuth callback and token processing
                JwtAuthFilter.class,         // Validates JWTs and attaches user identity to requests
                CorsPreflightResource.class, // Handles HTTP OPTIONS requests for CORS preflight
                CorsFilter.class,            // Adds CORS headers to all API responses
                JacksonFeature.class,        // Enables automatic JSON serialization/deserialization using Jackson
                JacksonConfig.class          // Customizes Jackson to handle Java time objects (e.g., LocalDateTime)
        );
    }
}
