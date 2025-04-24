package com.grittonbelldev.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;

public class MealRequestDto {
    @NotNull
    private String mealName;

    @NotNull
    private LocalDateTime timeEaten;

    private List<FoodEntryDto> foods;

    // getters & setters
    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public LocalDateTime getTimeEaten() { return timeEaten; }
    public void setTimeEaten(LocalDateTime timeEaten) { this.timeEaten = timeEaten; }

    public List<FoodEntryDto> getFoods() { return foods; }
    public void setFoods(List<FoodEntryDto> foods) { this.foods = foods; }
}