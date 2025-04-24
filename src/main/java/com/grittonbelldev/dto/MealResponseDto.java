package com.grittonbelldev.dto;

import com.grittonbelldev.dto.FoodEntryDto;

import java.time.LocalDateTime;
import java.util.List;

public class MealResponseDto {
    private Long id;
    private String mealName;
    private LocalDateTime timeEaten;
    private List<FoodEntryDto> foods;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public LocalDateTime getTimeEaten() { return timeEaten; }
    public void setTimeEaten(LocalDateTime timeEaten) { this.timeEaten = timeEaten; }

    public List<FoodEntryDto> getFoods() { return foods; }
    public void setFoods(List<FoodEntryDto> foods) { this.foods = foods; }
}
