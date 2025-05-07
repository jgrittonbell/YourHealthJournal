package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity representing a meal entry associated with a user.
 *
 * <p>Each meal includes a name, timestamp for when it was eaten, a flag
 * indicating if it is a favorite, and a list of food items consumed during the meal.
 * This entity maintains a many-to-one relationship with {@link User} and a one-to-many
 * relationship with {@link FoodMealJournal}.</p>
 */
@Entity
@Table(name = "Meal")
public class Meal {

    /** Primary key for the Meal. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    /** Many-to-one association with the user who logged this meal. */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Descriptive name for the meal (e.g., "Lunch", "Dinner", "Post-Workout"). */
    @Column(name = "meal_name", nullable = false, length = 255)
    private String mealName;

    /** Timestamp when the meal was eaten. Defaults to the current time. */
    @Column(name = "time_eaten", nullable = false)
    private LocalDateTime timeEaten = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    /** Indicates whether this meal has been marked as a favorite by the user. */
    @Column(name = "is_favorite", nullable = false)
    private boolean isFavorite = false;

    /** List of all food entries that were part of this meal. */
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FoodMealJournal> foodMealEntries = new ArrayList<>();

    // ---------------- Constructors ----------------

    /** No-arg constructor for JPA. */
    public Meal() {}

    /**
     * Constructs a Meal with specified user, name, timestamp, and favorite flag.
     *
     * @param user       the user who created the meal
     * @param mealName   name/label of the meal
     * @param timeEaten  when the meal was consumed
     * @param isFavorite whether the meal is marked as a favorite
     */
    public Meal(User user, String mealName, LocalDateTime timeEaten, boolean isFavorite) {
        this.user = user;
        this.mealName = mealName;
        this.timeEaten = (timeEaten != null)
                ? timeEaten.truncatedTo(ChronoUnit.SECONDS)
                : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
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
     * Gets meal name.
     *
     * @return the meal name
     */
    public String getMealName() { return mealName; }

    /**
     * Sets meal name.
     *
     * @param mealName the meal name
     */
    public void setMealName(String mealName) { this.mealName = mealName; }

    /**
     * Gets time eaten.
     *
     * @return the time eaten
     */
    public LocalDateTime getTimeEaten() { return timeEaten; }

    /**
     * Sets time eaten.
     *
     * @param timeEaten the time eaten
     */
    public void setTimeEaten(LocalDateTime timeEaten) {
        this.timeEaten = (timeEaten != null)
                ? timeEaten.truncatedTo(ChronoUnit.SECONDS)
                : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

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

    /**
     * Gets food meal entries.
     *
     * @return the food meal entries
     */
    public List<FoodMealJournal> getFoodMealEntries() { return foodMealEntries; }

    /**
     * Sets food meal entries.
     *
     * @param foodMealEntries the food meal entries
     */
    public void setFoodMealEntries(List<FoodMealJournal> foodMealEntries) { this.foodMealEntries = foodMealEntries; }

    /**
     * Adds a new food journal entry to the meal.
     *
     * @param foodMealEntry the journal entry to associate with this meal
     */
    public void addFoodMealEntry(FoodMealJournal foodMealEntry) {
        foodMealEntries.add(foodMealEntry);
        foodMealEntry.setMeal(this);
    }

    /**
     * Removes a food journal entry from the meal.
     *
     * @param foodMealEntry the entry to remove
     */
    public void removeFoodMealEntry(FoodMealJournal foodMealEntry) {
        foodMealEntries.remove(foodMealEntry);
        foodMealEntry.setMeal(null);
    }

    // ---------------- Object overrides ----------------

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
