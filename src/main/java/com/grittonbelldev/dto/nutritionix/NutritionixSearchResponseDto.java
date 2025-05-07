package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the full response structure returned by the Nutritionix
 * "instant" search endpoint.
 *
 * <p>
 * This DTO includes two main result lists:
 * <ul>
 *   <li>{@code branded} – pre-packaged and name-brand food items</li>
 *   <li>{@code common} – generic or commonly eaten food items</li>
 * </ul>
 * Jackson annotations are used to bind the JSON fields to the corresponding Java properties.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutritionixSearchResponseDto {

    /** List of branded food items returned in the search result. */
    @JsonProperty("branded")
    private List<BrandedItem> branded;

    /** List of common food items returned in the search result. */
    @JsonProperty("common")
    private List<CommonItem> common;

    /**
     * Get the list of branded food items.
     *
     * @return list of branded items
     */
    public List<BrandedItem> getBranded() {
        return branded;
    }

    /**
     * Set the list of branded food items.
     *
     * @param branded the list to assign
     */
    public void setBranded(List<BrandedItem> branded) {
        this.branded = branded;
    }

    /**
     * Get the list of common food items.
     *
     * @return list of common items
     */
    public List<CommonItem> getCommon() {
        return common;
    }

    /**
     * Set the list of common food items.
     *
     * @param common the list to assign
     */
    public void setCommon(List<CommonItem> common) {
        this.common = common;
    }
}
