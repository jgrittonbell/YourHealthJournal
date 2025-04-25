package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO representing a single food item from the Nutritionix API.
 * <p>
 * Used both for instant search results and detailed item fetches.
 * </p>
 */
public class NutritionixFoodDto {
    /**
     * Unique Nutritionix identifier for the item (e.g., "51db37c317fdee1a0dc9c48a").
     */
    @JsonProperty("nix_item_id")
    private String nixItemId;

    /**
     * Name of the food item (common or branded).
     */
    @JsonProperty("food_name")
    private String foodName;

    /**
     * Calories per serving (detailed endpoint only; may be null for instant search).
     */
    private Double calories;

    /**
     * Total carbohydrates per serving (detailed endpoint only).
     */
    @JsonProperty("nf_total_carbohydrate")
    private Double carbs;

    /**
     * Total protein per serving (detailed endpoint only).
     */
    @JsonProperty("nf_protein")
    private Double protein;

    /**
     * Total fat per serving (detailed endpoint only).
     */
    @JsonProperty("nf_total_fat")
    private Double fat;

    // Getters and setters

    /**
     * Gets nix item id.
     *
     * @return the nix item id
     */
    public String getNixItemId() {
        return nixItemId;
    }

    /**
     * Sets nix item id.
     *
     * @param nixItemId the nix item id
     */
    public void setNixItemId(String nixItemId) {
        this.nixItemId = nixItemId;
    }

    /**
     * Gets food name.
     *
     * @return the food name
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * Sets food name.
     *
     * @param foodName the food name
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     * Gets calories.
     *
     * @return the calories
     */
    public Double getCalories() {
        return calories;
    }

    /**
     * Sets calories.
     *
     * @param calories the calories
     */
    public void setCalories(Double calories) {
        this.calories = calories;
    }

    /**
     * Gets carbs.
     *
     * @return the carbs
     */
    public Double getCarbs() {
        return carbs;
    }

    /**
     * Sets carbs.
     *
     * @param carbs the carbs
     */
    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    /**
     * Gets protein.
     *
     * @return the protein
     */
    public Double getProtein() {
        return protein;
    }

    /**
     * Sets protein.
     *
     * @param protein the protein
     */
    public void setProtein(Double protein) {
        this.protein = protein;
    }

    /**
     * Gets fat.
     *
     * @return the fat
     */
    public Double getFat() {
        return fat;
    }

    /**
     * Sets fat.
     *
     * @param fat the fat
     */
    public void setFat(Double fat) {
        this.fat = fat;
    }
}