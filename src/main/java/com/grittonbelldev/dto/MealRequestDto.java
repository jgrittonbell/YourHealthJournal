package com.grittonbelldev.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * Request DTO for creating or updating a meal entry.
 * <p>
 * Includes the meal name, time eaten, and optional list of foods with serving sizes.
 * </p>
 */
public class MealRequestDto {
    /**
     * Human-readable name for the meal (e.g., "Breakfast").
     * Must not be null or blank.
     */
    @NotNull
    private String mealName;

    /**
     * Timestamp when the meal was eaten.
     * Must not be null.
     */
    @NotNull
    private LocalDateTime timeEaten;

    /**
     * Optional list of foods and serving sizes included in the meal.
     */
    private List<FoodEntryDto> foods;

    // --- Getters and Setters ---

    /**
     * Gets meal name.
     *
     * @return the meal name
     */
    public String getMealName() {
        return mealName;
    }

    /**
     * Sets meal name.
     *
     * @param mealName the meal name
     */
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    /**
     * Gets time eaten.
     *
     * @return the time eaten
     */
    public LocalDateTime getTimeEaten() {
        return timeEaten;
    }

    /**
     * Sets time eaten.
     *
     * @param timeEaten the time eaten
     */
    public void setTimeEaten(LocalDateTime timeEaten) {
        this.timeEaten = timeEaten;
    }

    /**
     * Gets foods.
     *
     * @return the foods
     */
    public List<FoodEntryDto> getFoods() {
        return foods;
    }

    /**
     * Sets foods.
     *
     * @param foods the foods
     */
    public void setFoods(List<FoodEntryDto> foods) {
        this.foods = foods;
    }
}