package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;




public class Food {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "food_name", nullable = false, length = 255)
    private String foodName;

    @Column(name = "time_eaten", nullable = false)
    private LocalDateTime timeEaten;

    @Column(name = "meal_category", length = 50)
    private String mealCategory = "uncategorized";

    @Column(name = "fat", nullable = false, precision = 5, scale = 2)
    private Double fat;

    @Column(name = "protein", nullable = false, precision = 5, scale = 2)
    private Double protein;

    @Column(name = "carbs", nullable = false, precision = 5, scale = 2)
    private Double carbs;

    @Column(name = "calories", nullable = false, precision = 6, scale = 2)
    private Double calories;

    @Column(name = "cholesterol", precision = 5, scale = 2)
    private Double cholesterol;

    @Column(name = "sodium", precision = 5, scale = 2)
    private Double sodium;

    @Column(name = "fiber", precision = 5, scale = 2)
    private Double fiber;

    @Column(name = "sugar", precision = 5, scale = 2)
    private Double sugar;

    @Column(name = "added_sugar", precision = 5, scale = 2)
    private Double addedSugar;

    @Column(name = "vitamin_d", precision = 5, scale = 2)
    private Double vitaminD;

    @Column(name = "calcium", precision = 5, scale = 2)
    private Double calcium;

    @Column(name = "iron", precision = 5, scale = 2)
    private Double iron;

    @Column(name = "potassium", precision = 5, scale = 2)
    private Double potassium;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Food() {}

    public Food(Long userId, String foodName, LocalDateTime timeEaten, Double fat, Double protein, Double carbs, Double calories) {
        this.userId = userId;
        this.foodName = foodName;
        this.timeEaten = timeEaten;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
        this.calories = calories;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public LocalDateTime getTimeEaten() {
        return timeEaten;
    }

    public void setTimeEaten(LocalDateTime timeEaten) {
        this.timeEaten = timeEaten;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Double getSodium() {
        return sodium;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public Double getSugar() {
        return sugar;
    }

    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }

    public Double getAddedSugar() {
        return addedSugar;
    }

    public void setAddedSugar(Double addedSugar) {
        this.addedSugar = addedSugar;
    }

    public Double getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(Double vitaminD) {
        this.vitaminD = vitaminD;
    }

    public Double getCalcium() {
        return calcium;
    }

    public void setCalcium(Double calcium) {
        this.calcium = calcium;
    }

    public Double getIron() {
        return iron;
    }

    public void setIron(Double iron) {
        this.iron = iron;
    }

    public Double getPotassium() {
        return potassium;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", userId=" + userId +
                ", foodName='" + foodName + '\'' +
                ", timeEaten=" + timeEaten +
                ", mealCategory='" + mealCategory + '\'' +
                ", fat=" + fat +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", calories=" + calories +
                ", cholesterol=" + cholesterol +
                ", sodium=" + sodium +
                ", fiber=" + fiber +
                ", sugar=" + sugar +
                ", addedSugar=" + addedSugar +
                ", vitaminD=" + vitaminD +
                ", calcium=" + calcium +
                ", iron=" + iron +
                ", potassium=" + potassium +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}


