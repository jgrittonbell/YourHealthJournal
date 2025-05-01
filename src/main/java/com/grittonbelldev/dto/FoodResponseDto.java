package com.grittonbelldev.dto;

import java.io.Serializable;

/**
 * DTO for returning basic food info in listings (e.g. favorites).
 */
public class FoodResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The internal database ID of the food. */
    private Long id;

    /** The (display) name of the food. */
    private String foodName;

    public FoodResponseDto() {}

    public FoodResponseDto(Long id, String foodName) {
        this.id = id;
        this.foodName = foodName;
    }

    /**
     * @return the food’s internal ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id set the food’s internal ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name of the food
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * @param foodName set the food’s name
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
