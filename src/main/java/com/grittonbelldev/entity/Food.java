package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@Entity
@Table(name = "Food")
public class Food {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "food_name", nullable = false, length = 255)
    private String foodName;

    @Column(name = "fat", nullable = false)
    private Double fat;

    @Column(name = "protein", nullable = false)
    private Double protein;

    @Column(name = "carbs", nullable = false)
    private Double carbs;

    @Column(name = "calories", nullable = false)
    private Double calories;

    @Column(name = "cholesterol")
    private Double cholesterol;

    @Column(name = "sodium")
    private Double sodium;

    @Column(name = "fiber")
    private Double fiber;

    @Column(name = "sugar")
    private Double sugar;

    @Column(name = "added_sugar")
    private Double addedSugar;

    @Column(name = "vitamin_d")
    private Double vitaminD;

    @Column(name = "calcium")
    private Double calcium;

    @Column(name = "iron")
    private Double iron;

    @Column(name = "potassium")
    private Double potassium;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // Constructors
    public Food() {}

    public Food(String foodName, Double fat, Double protein, Double carbs, Double calories) {
        this.foodName = foodName;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
        this.calories = calories;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }

    public Double getProtein() { return protein; }
    public void setProtein(Double protein) { this.protein = protein; }

    public Double getCarbs() { return carbs; }
    public void setCarbs(Double carbs) { this.carbs = carbs; }

    public Double getCalories() { return calories; }
    public void setCalories(Double calories) { this.calories = calories; }

    public Double getCholesterol() { return cholesterol; }
    public void setCholesterol(Double cholesterol) { this.cholesterol = cholesterol; }

    public Double getSodium() { return sodium; }
    public void setSodium(Double sodium) { this.sodium = sodium; }

    public Double getFiber() { return fiber; }
    public void setFiber(Double fiber) { this.fiber = fiber; }

    public Double getSugar() { return sugar; }
    public void setSugar(Double sugar) { this.sugar = sugar; }

    public Double getAddedSugar() { return addedSugar; }
    public void setAddedSugar(Double addedSugar) { this.addedSugar = addedSugar; }

    public Double getVitaminD() { return vitaminD; }
    public void setVitaminD(Double vitaminD) { this.vitaminD = vitaminD; }

    public Double getCalcium() { return calcium; }
    public void setCalcium(Double calcium) { this.calcium = calcium; }

    public Double getIron() { return iron; }
    public void setIron(Double iron) { this.iron = iron; }

    public Double getPotassium() { return potassium; }
    public void setPotassium(Double potassium) { this.potassium = potassium; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Equals, HashCode, and ToString
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


