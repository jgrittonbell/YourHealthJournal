package com.grittonbelldev.dto;

import javax.validation.constraints.NotNull;

public class FoodEntryDto {
    @NotNull
    private Long foodId;

    @NotNull
    private Double servingSize;

    // getters & setters
    public Long getFoodId() { return foodId; }
    public void setFoodId(Long foodId) { this.foodId = foodId; }

    public Double getServingSize() { return servingSize; }
    public void setServingSize(Double servingSize) { this.servingSize = servingSize; }
}
