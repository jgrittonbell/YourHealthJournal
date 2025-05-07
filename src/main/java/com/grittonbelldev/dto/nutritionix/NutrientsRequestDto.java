package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data-transfer object representing the request payload sent to
 * the Nutritionix /v2/natural/nutrients endpoint.
 *
 * <p>
 * The endpoint accepts free-form natural-language input describing
 * food items (e.g. "2 eggs and a banana") and returns detailed
 * nutrient information.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutrientsRequestDto {

    /** The free-form query string describing the food input. */
    @JsonProperty("query")
    private String query;

    /** Default constructor for deserialization. */
    public NutrientsRequestDto() { }

    /**
     * Constructor to initialize the query value.
     *
     * @param query the natural-language food description
     */
    public NutrientsRequestDto(String query) {
        this.query = query;
    }

    /**
     * Get the food description query string.
     *
     * @return the query value
     */
    public String getQuery() {
        return query;
    }

    /**
     * Set the food description query string.
     *
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }
}
