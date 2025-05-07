package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * Entity representing a food item and its nutritional attributes.
 *
 * <p>This entity models both branded and generic food entries, capturing
 * macronutrient and micronutrient values as well as optional notes for custom entries.</p>
 */
@Entity
@Table(name = "Food")
public class Food {

    /** Primary key for the food item. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    /** The name of the food (e.g., "Apple", "Cheddar Cheese"). */
    @Column(name = "food_name", nullable = false, length = 255)
    private String foodName;

    /** Total fat content in grams. */
    @Column(name = "fat", nullable = false)
    private Double fat;

    /** Total protein content in grams. */
    @Column(name = "protein", nullable = false)
    private Double protein;

    /** Total carbohydrate content in grams. */
    @Column(name = "carbs", nullable = false)
    private Double carbs;

    /** Total calories in kilocalories. */
    @Column(name = "calories", nullable = false)
    private Double calories;

    /** Cholesterol content in milligrams, if available. */
    @Column(name = "cholesterol")
    private Double cholesterol;

    /** Sodium content in milligrams, if available. */
    @Column(name = "sodium")
    private Double sodium;

    /** Fiber content in grams, if available. */
    @Column(name = "fiber")
    private Double fiber;

    /** Total sugar content in grams, if available. */
    @Column(name = "sugar")
    private Double sugar;

    /** Added sugar content in grams, if available. */
    @Column(name = "added_sugar")
    private Double addedSugar;

    /** Vitamin D content in micrograms, if available. */
    @Column(name = "vitamin_d")
    private Double vitaminD;

    /** Calcium content in milligrams, if available. */
    @Column(name = "calcium")
    private Double calcium;

    /** Iron content in milligrams, if available. */
    @Column(name = "iron")
    private Double iron;

    /** Potassium content in milligrams, if available. */
    @Column(name = "potassium")
    private Double potassium;

    /** Freeform text for any notes or source information. */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // ---------------- Constructors ----------------

    /** No-argument constructor for JPA. */
    public Food() {}

    /**
     * Minimal constructor for creating a food item with required macro fields.
     *
     * @param foodName the name of the food
     * @param fat total fat in grams
     * @param protein total protein in grams
     * @param carbs total carbohydrates in grams
     * @param calories total calories
     */
    public Food(String foodName, Double fat, Double protein, Double carbs, Double calories) {
        this.foodName = foodName;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
        this.calories = calories;
    }

    // ---------------- Getters and Setters ----------------

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() { return id; }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Gets food name.
     *
     * @return the food name
     */
    public String getFoodName() { return foodName; }

    /**
     * Sets food name.
     *
     * @param foodName the food name
     */
    public void setFoodName(String foodName) { this.foodName = foodName; }

    /**
     * Gets fat.
     *
     * @return the fat
     */
    public Double getFat() { return fat; }

    /**
     * Sets fat.
     *
     * @param fat the fat
     */
    public void setFat(Double fat) { this.fat = fat; }

    /**
     * Gets protein.
     *
     * @return the protein
     */
    public Double getProtein() { return protein; }

    /**
     * Sets protein.
     *
     * @param protein the protein
     */
    public void setProtein(Double protein) { this.protein = protein; }

    /**
     * Gets carbs.
     *
     * @return the carbs
     */
    public Double getCarbs() { return carbs; }

    /**
     * Sets carbs.
     *
     * @param carbs the carbs
     */
    public void setCarbs(Double carbs) { this.carbs = carbs; }

    /**
     * Gets calories.
     *
     * @return the calories
     */
    public Double getCalories() { return calories; }

    /**
     * Sets calories.
     *
     * @param calories the calories
     */
    public void setCalories(Double calories) { this.calories = calories; }

    /**
     * Gets cholesterol.
     *
     * @return the cholesterol
     */
    public Double getCholesterol() { return cholesterol; }

    /**
     * Sets cholesterol.
     *
     * @param cholesterol the cholesterol
     */
    public void setCholesterol(Double cholesterol) { this.cholesterol = cholesterol; }

    /**
     * Gets sodium.
     *
     * @return the sodium
     */
    public Double getSodium() { return sodium; }

    /**
     * Sets sodium.
     *
     * @param sodium the sodium
     */
    public void setSodium(Double sodium) { this.sodium = sodium; }

    /**
     * Gets fiber.
     *
     * @return the fiber
     */
    public Double getFiber() { return fiber; }

    /**
     * Sets fiber.
     *
     * @param fiber the fiber
     */
    public void setFiber(Double fiber) { this.fiber = fiber; }

    /**
     * Gets sugar.
     *
     * @return the sugar
     */
    public Double getSugar() { return sugar; }

    /**
     * Sets sugar.
     *
     * @param sugar the sugar
     */
    public void setSugar(Double sugar) { this.sugar = sugar; }

    /**
     * Gets added sugar.
     *
     * @return the added sugar
     */
    public Double getAddedSugar() { return addedSugar; }

    /**
     * Sets added sugar.
     *
     * @param addedSugar the added sugar
     */
    public void setAddedSugar(Double addedSugar) { this.addedSugar = addedSugar; }

    /**
     * Gets vitamin d.
     *
     * @return the vitamin d
     */
    public Double getVitaminD() { return vitaminD; }

    /**
     * Sets vitamin d.
     *
     * @param vitaminD the vitamin d
     */
    public void setVitaminD(Double vitaminD) { this.vitaminD = vitaminD; }

    /**
     * Gets calcium.
     *
     * @return the calcium
     */
    public Double getCalcium() { return calcium; }

    /**
     * Sets calcium.
     *
     * @param calcium the calcium
     */
    public void setCalcium(Double calcium) { this.calcium = calcium; }

    /**
     * Gets iron.
     *
     * @return the iron
     */
    public Double getIron() { return iron; }

    /**
     * Sets iron.
     *
     * @param iron the iron
     */
    public void setIron(Double iron) { this.iron = iron; }

    /**
     * Gets potassium.
     *
     * @return the potassium
     */
    public Double getPotassium() { return potassium; }

    /**
     * Sets potassium.
     *
     * @param potassium the potassium
     */
    public void setPotassium(Double potassium) { this.potassium = potassium; }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() { return notes; }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) { this.notes = notes; }

    // ---------------- Object overrides ----------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(id, food.id) &&
                Objects.equals(foodName, food.foodName) &&
                Objects.equals(fat, food.fat) &&
                Objects.equals(protein, food.protein) &&
                Objects.equals(carbs, food.carbs) &&
                Objects.equals(calories, food.calories) &&
                Objects.equals(cholesterol, food.cholesterol) &&
                Objects.equals(sodium, food.sodium) &&
                Objects.equals(fiber, food.fiber) &&
                Objects.equals(sugar, food.sugar) &&
                Objects.equals(addedSugar, food.addedSugar) &&
                Objects.equals(vitaminD, food.vitaminD) &&
                Objects.equals(calcium, food.calcium) &&
                Objects.equals(iron, food.iron) &&
                Objects.equals(potassium, food.potassium) &&
                Objects.equals(notes, food.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodName, fat, protein, carbs, calories, cholesterol, sodium, fiber, sugar, addedSugar, vitaminD, calcium, iron, potassium, notes);
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", foodName='" + foodName + '\'' +
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
                '}';
    }
}
