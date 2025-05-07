package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * Entity representing a user's favorite food or meal.
 *
 * <p>
 * This entity allows linking a user to a favorite food or meal. Either the `meal` or `food` field
 * may be null, allowing for favoriting of individual foods or complete meals. The combination of
 * user, meal, and food must be unique as enforced by a compound constraint.
 * </p>
 */
@Entity
@Table(name = "FavoriteItems",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "meal_id", "food_id"}))
public class FavoriteItem {

    /** Primary key identifier for the favorite item entry. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    /** The user who marked the item as a favorite. */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** The meal marked as favorite (optional). */
    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = true)
    private Meal meal;

    /** The food item marked as favorite (optional). */
    @ManyToOne
    @JoinColumn(name = "food_id", nullable = true)
    private Food food;

    /** Indicates whether the item is currently favorited. */
    @Column(name = "is_favorite", nullable = false)
    private boolean isFavorite = true;

    // ---------------- Constructors ----------------

    /** No-argument constructor for JPA. */
    public FavoriteItem() {}

    /**
     * Constructor for manually creating a favorite item instance.
     *
     * @param user the user who owns this favorite entry
     * @param meal the meal being favorited, or null
     * @param food the food being favorited, or null
     * @param isFavorite flag indicating if the item is currently marked as favorite
     */
    public FavoriteItem(User user, Meal meal, Food food, boolean isFavorite) {
        this.user = user;
        this.meal = meal;
        this.food = food;
        this.isFavorite = isFavorite;
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
     * Gets user.
     *
     * @return the user
     */
    public User getUser() { return user; }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) { this.user = user; }

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
     * Is favorite boolean.
     *
     * @return the boolean
     */
    public boolean isFavorite() { return isFavorite; }

    /**
     * Sets favorite.
     *
     * @param favorite the favorite
     */
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    // ---------------- Object overrides ----------------

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
