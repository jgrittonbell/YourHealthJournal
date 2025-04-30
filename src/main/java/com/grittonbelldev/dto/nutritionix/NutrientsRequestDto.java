package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple wrapper for the natural‐language /v2/natural/nutrients POST body.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutrientsRequestDto {

    @JsonProperty("query")
    private String query;

    public NutrientsRequestDto() { }

    public NutrientsRequestDto(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
