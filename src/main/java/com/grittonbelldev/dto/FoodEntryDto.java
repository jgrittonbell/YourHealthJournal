package com.grittonbelldev.dto;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object representing a food entry in a meal.
 * <p>
 * Contains the ID of the food item and the serving size.
 * Used in both request and response payloads for meals.
 * </p>
 */
public class FoodEntryDto {
    /**
     * Unique identifier of the Food entity.
     * Must not be null when adding or editing a food entry.
     */
    @NotNull
    private Long foodId;

    /**
     * Number of servings of the food in the meal.
     * Must not be null and should be a positive value.
     */
    @NotNull
    private Double servingSize;

    /**
     * Returns the food ID.
     *
     * @return the foodId
     */
    public Long getFoodId() {
        return foodId;
    }

    /**
     * Sets the food ID.
     *
     * @param foodId the foodId to set; must not be null
     */
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    /**
     * Returns the serving size.
     *
     * @return the servingSize
     */
    public Double getServingSize() {
        return servingSize;
    }

    /**
     * Sets the serving size.
     *
     * @param servingSize the servingSize to set; must not be null
     */
    public void setServingSize(Double servingSize) {
        this.servingSize = servingSize;
    }
}
