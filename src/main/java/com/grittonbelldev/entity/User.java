package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity representing a registered user in the system.
 *
 * <p>Each user is identified by a unique Cognito ID and stores basic profile details
 * such as name and email. This entity also holds relationships to all associated meals,
 * glucose readings, and favorited food items. The createdAt field is automatically
 * populated at instantiation and truncated to the nearest second for consistency.</p>
 */
@Entity
@Table(name = "Users")
public class User {

    /** Primary key for the user. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique identifier corresponding to the AWS Cognito identity. */
    @Column(name = "cognito_id", nullable = false, unique = true)
    private String cognitoId;

    /** First name of the user. */
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    /** Last name of the user. */
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    /** Email address for contact and identification purposes. */
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /** Timestamp representing when the user account was created. */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    /** One-to-many relationship: all meals logged by this user. */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    /** One-to-many relationship: all glucose readings recorded by this user. */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GlucoseReading> glucoseReadings = new ArrayList<>();

    /** One-to-many relationship: list of favorite food items selected by the user. */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteItem> favoriteItems = new ArrayList<>();

    // ---------------- Constructors ----------------

    /** No-argument constructor for JPA. */
    public User() {}

    /**
     * Constructs a User with the required identity and profile fields.
     *
     * @param cognitoId AWS Cognito identifier
     * @param firstName user's first name
     * @param lastName  user's last name
     * @param email     user's email address
     */
    public User(String cognitoId, String firstName, String lastName, String email) {
        this.cognitoId = cognitoId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
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
     * Gets cognito id.
     *
     * @return the cognito id
     */
    public String getCognitoId() { return cognitoId; }

    /**
     * Sets cognito id.
     *
     * @param cognitoId the cognito id
     */
    public void setCognitoId(String cognitoId) { this.cognitoId = cognitoId; }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() { return firstName; }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() { return lastName; }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() { return email; }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        // Normalize precision to seconds
        this.createdAt = (createdAt != null)
                ? createdAt.truncatedTo(ChronoUnit.SECONDS)
                : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Gets meals.
     *
     * @return the meals
     */
    public List<Meal> getMeals() { return meals; }
    public void setMeals(List<Meal> meals) { this.meals = meals; }

    /**
     * Add meal.
     *
     * @param meal the meal
     */
    public void addMeal(Meal meal) {
        meals.add(meal);
        meal.setUser(this);
    }

    /**
     * Remove meal.
     *
     * @param meal the meal
     */
    public void removeMeal(Meal meal) {
        meals.remove(meal);
        meal.setUser(null);
    }

    /**
     * Gets glucose readings.
     *
     * @return the glucose readings
     */
    public List<GlucoseReading> getGlucoseReadings() { return glucoseReadings; }
    public void setGlucoseReadings(List<GlucoseReading> glucoseReadings) { this.glucoseReadings = glucoseReadings; }

    /**
     * Add glucose reading.
     *
     * @param reading the reading
     */
    public void addGlucoseReading(GlucoseReading reading) {
        glucoseReadings.add(reading);
        reading.setUser(this);
    }

    /**
     * Remove glucose reading.
     *
     * @param reading the reading
     */
    public void removeGlucoseReading(GlucoseReading reading) {
        glucoseReadings.remove(reading);
        reading.setUser(null);
    }

    /**
     * Gets favorite items.
     *
     * @return the favorite items
     */
    public List<FavoriteItem> getFavoriteItems() { return favoriteItems; }

    /**
     * Sets favorite items.
     *
     * @param favoriteItems the favorite items
     */
    public void setFavoriteItems(List<FavoriteItem> favoriteItems) { this.favoriteItems = favoriteItems; }

    /**
     * Add favorite item.
     *
     * @param favoriteItem the favorite item
     */
    public void addFavoriteItem(FavoriteItem favoriteItem) {
        favoriteItems.add(favoriteItem);
        favoriteItem.setUser(this);
    }

    /**
     * Remove favorite item.
     *
     * @param favoriteItem the favorite item
     */
    public void removeFavoriteItem(FavoriteItem favoriteItem) {
        favoriteItems.remove(favoriteItem);
        favoriteItem.setUser(null);
    }

    // ---------------- Object overrides ----------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cognitoId='" + cognitoId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
