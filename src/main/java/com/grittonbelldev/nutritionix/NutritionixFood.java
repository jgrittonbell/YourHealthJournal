package com.grittonbelldev.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NutritionixFood {

    @JsonProperty("food_name")
    private String foodName;

    @JsonProperty("nf_calories")
    private double calories;

    @JsonProperty("nf_total_fat")
    private double fat;

    @JsonProperty("nf_protein")
    private double protein;

    @JsonProperty("nf_total_carbohydrate")
    private double carbs;

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCalories() {
        return calories;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFat() {
        return fat;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getProtein() {
        return protein;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getCarbs() {
        return carbs;
    }

//    @Override
//    public String toString() {
//        return "NutritionixFood{" +
//                "foodName='" + foodName + '\'' +
//                ", calories=" + calories +
//                ", fat=" + fat +
//                ", protein=" + protein +
//                ", carbs=" + carbs +
//                '}';
//    }
}
