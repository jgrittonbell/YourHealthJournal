package com.grittonbelldev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO representing a food item returned from the API.
 * <p>
 * Contains the full nutritional profile of a food entity,
 * suitable for displaying favorites, search results, or meal analysis.
 * </p>
 */
public class FoodResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Unique database identifier for the food. */
    @JsonProperty("id")
    private Long id;

    /** Display name of the food item. */
    @JsonProperty("foodName")
    private String foodName;

    /** Fat content in grams. */
    @JsonProperty("fat")
    private Double fat;

    /** Protein content in grams. */
    @JsonProperty("protein")
    private Double protein;

    /** Carbohydrate content in grams. */
    @JsonProperty("carbs")
    private Double carbs;

    /** Total caloric value in kilocalories. */
    @JsonProperty("calories")
    private Double calories;

    /** Cholesterol content in milligrams. */
    @JsonProperty("cholesterol")
    private Double cholesterol;

    /** Sodium content in milligrams. */
    @JsonProperty("sodium")
    private Double sodium;

    /** Dietary fiber in grams. */
    @JsonProperty("fiber")
    private Double fiber;

    /** Total sugar content in grams. */
    @JsonProperty("sugar")
    private Double sugar;

    /** Added sugar in grams. */
    @JsonProperty("addedSugar")
    private Double addedSugar;

    /** Vitamin D in micrograms. */
    @JsonProperty("vitaminD")
    private Double vitaminD;

    /** Calcium in milligrams. */
    @JsonProperty("calcium")
    private Double calcium;

    /** Iron in milligrams. */
    @JsonProperty("iron")
    private Double iron;

    /** Potassium in milligrams. */
    @JsonProperty("potassium")
    private Double potassium;

    /** Optional freeform notes or description. */
    @JsonProperty("notes")
    private String notes;

    // ──────────────────────── Constructors ────────────────────────

    public FoodResponseDto() {}

    public FoodResponseDto(Long id, String foodName) {
        this.id = id;
        this.foodName = foodName;
    }

    // ──────────────────────── Getters & Setters ────────────────────────

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
     * Gets cholesterol.
     *
     * @return the cholesterol
     */
    public Double getCholesterol() {
        return cholesterol;
    }

    /**
     * Sets cholesterol.
     *
     * @param cholesterol the cholesterol
     */
    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    /**
     * Gets sodium.
     *
     * @return the sodium
     */
    public Double getSodium() {
        return sodium;
    }

    /**
     * Sets sodium.
     *
     * @param sodium the sodium
     */
    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    /**
     * Gets fiber.
     *
     * @return the fiber
     */
    public Double getFiber() {
        return fiber;
    }

    /**
     * Sets fiber.
     *
     * @param fiber the fiber
     */
    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    /**
     * Gets sugar.
     *
     * @return the sugar
     */
    public Double getSugar() {
        return sugar;
    }

    /**
     * Sets sugar.
     *
     * @param sugar the sugar
     */
    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }

    /**
     * Gets added sugar.
     *
     * @return the added sugar
     */
    public Double getAddedSugar() {
        return addedSugar;
    }

    /**
     * Sets added sugar.
     *
     * @param addedSugar the added sugar
     */
    public void setAddedSugar(Double addedSugar) {
        this.addedSugar = addedSugar;
    }

    /**
     * Gets vitamin d.
     *
     * @return the vitamin d
     */
    public Double getVitaminD() {
        return vitaminD;
    }

    /**
     * Sets vitamin d.
     *
     * @param vitaminD the vitamin d
     */
    public void setVitaminD(Double vitaminD) {
        this.vitaminD = vitaminD;
    }

    /**
     * Gets calcium.
     *
     * @return the calcium
     */
    public Double getCalcium() {
        return calcium;
    }

    /**
     * Sets calcium.
     *
     * @param calcium the calcium
     */
    public void setCalcium(Double calcium) {
        this.calcium = calcium;
    }

    /**
     * Gets iron.
     *
     * @return the iron
     */
    public Double getIron() {
        return iron;
    }

    /**
     * Sets iron.
     *
     * @param iron the iron
     */
    public void setIron(Double iron) {
        this.iron = iron;
    }

    /**
     * Gets potassium.
     *
     * @return the potassium
     */
    public Double getPotassium() {
        return potassium;
    }

    /**
     * Sets potassium.
     *
     * @param potassium the potassium
     */
    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
