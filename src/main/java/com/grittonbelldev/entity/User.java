package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "cognito_id", nullable = false, updatable = false, unique = true)
    private String cognitoId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GlucoseReading> glucoseReadings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteItem> favoriteItems = new ArrayList<>();

    // Constructors
    public User() {}

    public User(String firstName, String lastName, String email, LocalDateTime createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = (createdAt != null)
                ? createdAt.truncatedTo(ChronoUnit.SECONDS)
                : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public String getCognitoId() {
        return cognitoId;
    }

    public void setCognitoId(String cognitoId) {
        this.cognitoId = cognitoId;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = (createdAt != null) ? createdAt.truncatedTo(ChronoUnit.SECONDS) : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public List<Meal> getMeals() { return meals; }
    public void setMeals(List<Meal> meals) { this.meals = meals; }

    public void addMeal(Meal meal) {
        meals.add(meal);
        meal.setUser(this);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
        meal.setUser(null);
    }

    public List<GlucoseReading> getGlucoseReadings() { return glucoseReadings; }
    public void setGlucoseReadings(List<GlucoseReading> glucoseReadings) { this.glucoseReadings = glucoseReadings; }

    public void addGlucoseReading(GlucoseReading reading) {
        glucoseReadings.add(reading);
        reading.setUser(this);
    }

    public void removeGlucoseReading(GlucoseReading reading) {
        glucoseReadings.remove(reading);
        reading.setUser(null);
    }

    public List<FavoriteItem> getFavoriteItems() { return favoriteItems; }
    public void setFavoriteItems(List<FavoriteItem> favoriteItems) { this.favoriteItems = favoriteItems; }

    public void addFavoriteItem(FavoriteItem favoriteItem) {
        favoriteItems.add(favoriteItem);
        favoriteItem.setUser(this);
    }

    public void removeFavoriteItem(FavoriteItem favoriteItem) {
        favoriteItems.remove(favoriteItem);
        favoriteItem.setUser(null);
    }

    // Equals, HashCode, and ToString
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(cognitoId, user.cognitoId) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cognitoId, firstName, lastName, email, createdAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "cognitoId='" + cognitoId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
