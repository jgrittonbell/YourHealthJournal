package com.grittonbelldev.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Configures a customized Jackson {@link ObjectMapper} for use by the JAX-RS framework.
 *
 * This provider will be automatically discovered and registered by JAX-RS when JSON
 * serialization or deserialization is needed.
 *
 * Responsibilities:
 * <ul>
 *   <li>Registers support for {@code java.time.*} types such as {@code LocalDateTime}</li>
 *   <li>Disables serialization of dates as numeric timestamps</li>
 *   <li>Ensures consistent date formatting using ISO-8601 strings</li>
 * </ul>
 */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    // The shared ObjectMapper instance configured for the application
    private final ObjectMapper mapper;

    /**
     * Constructor configures the ObjectMapper with desired modules and settings.
     * Specifically, it enables support for Java 8 time types and disables timestamp-based date output.
     */
    public JacksonConfig() {
        mapper = new ObjectMapper()
                // Register JavaTimeModule to handle serialization of java.time.* types
                .registerModule(new JavaTimeModule())
                // Disable writing dates as UNIX timestamps; use ISO-8601 strings instead
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Returns the configured ObjectMapper for the given class type.
     *
     * @param type the class type for which the mapper is being requested
     * @return the configured ObjectMapper
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
