package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * Entity representing a many-to-many association between meals and food items.
 *
 * <p>This entity serves as a join table between {@link Meal} and {@link Food},
 * capturing the serving size of each food item consumed in a specific meal.
 * Each entry in this table corresponds to one food item and its portion within one meal.</p>
 */
@Entity
@Table(name = "FoodMealJournal")
public class FoodMealJournal {

    /** Primary key for the food-meal journal entry. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    /** Many-to-one association with the meal to which the food belongs. */
    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    /** Many-to-one association with the food item included in the meal. */
    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    /** Quantity of the food item consumed in the meal, expressed in serving units. */
    @Column(name = "serving_size", nullable = false)
    private Double servingSize;

    // ---------------- Constructors ----------------

    /** No-arg constructor for JPA. */
    public FoodMealJournal() {}

    /**
     * Constructs a journal entry for the specified meal, food, and serving size.
     *
     * @param meal the meal in which the food was consumed
     * @param food the food item consumed
     * @param servingSize the amount consumed, in serving units
     */
    public FoodMealJournal(Meal meal, Food food, Double servingSize) {
        this.meal = meal;
        this.food = food;
        this.servingSize = servingSize;
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
     * Gets meal.
     *
     * @return the meal
     */
    public Meal getMeal() { return meal; }

    /**
     * Sets meal.
     *
     * @param meal the meal
     */
    public void setMeal(Meal meal) { this.meal = meal; }

    /**
     * Gets food.
     *
     * @return the food
     */
    public Food getFood() { return food; }

    /**
     * Sets food.
     *
     * @param food the food
     */
    public void setFood(Food food) { this.food = food; }

    /**
     * Gets serving size.
     *
     * @return the serving size
     */
    public Double getServingSize() { return servingSize; }

    /**
     * Sets serving size.
     *
     * @param servingSize the serving size
     */
    public void setServingSize(Double servingSize) { this.servingSize = servingSize; }

    // ---------------- Object overrides ----------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodMealJournal that = (FoodMealJournal) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(meal, that.meal) &&
                Objects.equals(food, that.food) &&
                Objects.equals(servingSize, that.servingSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meal, food, servingSize);
    }

    @Override
    public String toString() {
        return "FoodMealJournal{" +
                "id=" + id +
                ", meal=" + meal +
                ", food=" + food +
                ", servingSize=" + servingSize +
                '}';
    }
}
