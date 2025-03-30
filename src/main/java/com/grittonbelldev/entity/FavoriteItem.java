package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Table(name = "FavoriteItems",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "meal_id", "food_id"}))
public class FavoriteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "cognito_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = true)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = true)
    private Food food;

    @Column(name = "is_favorite", nullable = false)
    private boolean isFavorite = true;

    // Constructors
    public FavoriteItem() {}

    public FavoriteItem(User user, Meal meal, Food food, boolean isFavorite) {
        this.user = user;
        this.meal = meal;
        this.food = food;
        this.isFavorite = isFavorite;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Meal getMeal() { return meal; }
    public void setMeal(Meal meal) { this.meal = meal; }

    public Food getFood() { return food; }
    public void setFood(Food food) { this.food = food; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    // Equals, HashCode, and ToString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteItem that = (FavoriteItem) o;
        return isFavorite == that.isFavorite &&
                Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(meal, that.meal) &&
                Objects.equals(food, that.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, meal, food, isFavorite);
    }

    @Override
    public String toString() {
        return "FavoriteItem{" +
                "id=" + id +
                ", user=" + user +
                ", meal=" + meal +
                ", food=" + food +
                ", isFavorite=" + isFavorite +
                '}';
    }
}

