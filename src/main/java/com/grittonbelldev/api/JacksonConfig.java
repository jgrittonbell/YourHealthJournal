package com.grittonbelldev.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * JAX-RS will auto-discover this and use it whenever it needs an ObjectMapper.
 * Registers support for java.time.* types and disables writing dates as numeric timestamps.
 */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JacksonConfig() {
        mapper = new ObjectMapper()
                // support java.time.* types (LocalDateTime, etc)
                .registerModule(new JavaTimeModule())
                // write them as ISO strings, not as timestamps
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
