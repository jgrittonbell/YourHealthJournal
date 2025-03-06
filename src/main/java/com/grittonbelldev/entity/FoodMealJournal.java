package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Table(name = "FoodMealJournal")
public class FoodMealJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(name = "serving_size", nullable = false)
    private Double servingSize;

    // Constructors
    public FoodMealJournal() {}

    public FoodMealJournal(Meal meal, Food food, Double servingSize) {
        this.meal = meal;
        this.food = food;
        this.servingSize = servingSize;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Meal getMeal() { return meal; }
    public void setMeal(Meal meal) { this.meal = meal; }

    public Food getFood() { return food; }
    public void setFood(Food food) { this.food = food; }

    public Double getServingSize() { return servingSize; }
    public void setServingSize(Double servingSize) { this.servingSize = servingSize; }

    // Equals, HashCode, and ToString
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
