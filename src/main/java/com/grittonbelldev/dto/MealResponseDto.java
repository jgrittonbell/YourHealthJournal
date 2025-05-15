package com.grittonbelldev.dto;

import com.grittonbelldev.dto.FoodEntryDto;
import com.grittonbelldev.entity.FoodMealJournal;
import com.grittonbelldev.entity.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Response DTO for a meal, including its ID, name, time eaten,
 * and the list of foods with serving sizes.
 * <p>
 * Used to return meal data to the client after creation or retrieval.
 * </p>
 */
public class MealResponseDto {
    /**
     * Unique identifier of the meal.
     */
    private Long id;

    /**
     * Name of the meal (e.g., "Lunch").
     */
    private String mealName;

    /**
     * Timestamp when the meal was eaten.
     */
    private LocalDateTime timeEaten;

    /**
     * List of foods included in the meal with their serving sizes.
     */
    private List<FoodEntryDto> foods;

    // --- Getters and Setters ---

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

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


    /**
     * Converts a Meal entity into a MealResponseDto.
     *
     * @param meal The Meal entity from the database
     * @return A mapped MealResponseDto with embedded food data
     */
    public static MealResponseDto fromEntity(Meal meal) {
        MealResponseDto dto = new MealResponseDto();
        dto.setId(meal.getId());
        dto.setMealName(meal.getMealName());
        dto.setTimeEaten(meal.getTimeEaten());

        if (meal.getFoodMealEntries() != null) {
            dto.setFoods(
                    meal.getFoodMealEntries().stream()
                            .map(entry -> {
                                FoodEntryDto foodDto = FoodEntryDto.fromEntity(entry.getFood());
                                foodDto.setServingSize(entry.getServingSize()); // inject serving size
                                return foodDto;
                            })
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

}
