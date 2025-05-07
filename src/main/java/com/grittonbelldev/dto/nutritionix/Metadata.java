package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the optional metadata object in Nutritionix responses.
 *
 * <p>
 * This class is currently empty but serves as a placeholder for any
 * future metadata fields that might be added by the Nutritionix API.
 * By using {@code @JsonIgnoreProperties(ignoreUnknown = true)},
 * it safely ignores any unmapped fields during deserialization.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
    // Intentionally left blank — structure may be populated in the future as needed
}
