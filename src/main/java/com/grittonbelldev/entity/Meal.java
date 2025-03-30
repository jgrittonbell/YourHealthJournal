package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Meal")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "cognito_id", nullable = false)
    private User user;

    @Column(name = "meal_name", nullable = false, length = 255)
    private String mealName;

    @Column(name = "time_eaten", nullable = false)
    private LocalDateTime timeEaten = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column(name = "is_favorite", nullable = false)
    private boolean isFavorite = false;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodMealJournal> foodMealEntries = new ArrayList<>();

    // Constructors
    public Meal() {}

    public Meal(User user, String mealName, LocalDateTime timeEaten, boolean isFavorite) {
        this.user = user;
        this.mealName = mealName;
        this.timeEaten = (timeEaten != null) ? timeEaten.truncatedTo(ChronoUnit.SECONDS) : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.isFavorite = isFavorite;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public LocalDateTime getTimeEaten() { return timeEaten; }
    public void setTimeEaten(LocalDateTime timeEaten) {
        this.timeEaten = (timeEaten != null) ? timeEaten.truncatedTo(ChronoUnit.SECONDS) : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public List<FoodMealJournal> getFoodMealEntries() { return foodMealEntries; }
    public void setFoodMealEntries(List<FoodMealJournal> foodMealEntries) { this.foodMealEntries = foodMealEntries; }

    public void addFoodMealEntry(FoodMealJournal foodMealEntry) {
        foodMealEntries.add(foodMealEntry);
        foodMealEntry.setMeal(this);
    }

    public void removeFoodMealEntry(FoodMealJournal foodMealEntry) {
        foodMealEntries.remove(foodMealEntry);
        foodMealEntry.setMeal(null);
    }

    // Equals, HashCode, and ToString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return isFavorite == meal.isFavorite &&
                Objects.equals(id, meal.id) &&
                Objects.equals(user, meal.user) &&
                Objects.equals(mealName, meal.mealName) &&
                Objects.equals(timeEaten, meal.timeEaten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, mealName, timeEaten, isFavorite);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", user=" + user +
                ", mealName='" + mealName + '\'' +
                ", timeEaten=" + timeEaten +
                ", isFavorite=" + isFavorite +
                '}';
    }
}