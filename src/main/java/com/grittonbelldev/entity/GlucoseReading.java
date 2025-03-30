package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "GlucoseReading")
public class GlucoseReading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "cognito_id", nullable = false)
    private User user;

    @Column(name = "glucose_level", nullable = false)
    private Double glucoseLevel;

    @Column(name = "measurement_time", nullable = false)
    private LocalDateTime measurementTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column(name = "measurement_source", nullable = false, length = 50)
    private String measurementSource;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // Constructors
    public GlucoseReading() {}

    public GlucoseReading(User user, Double glucoseLevel, LocalDateTime measurementTime, String measurementSource, String notes) {
        this.user = user;
        this.glucoseLevel = glucoseLevel;
        this.measurementTime = (measurementTime != null) ? measurementTime.truncatedTo(ChronoUnit.SECONDS) : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.measurementSource = measurementSource;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Double getGlucoseLevel() { return glucoseLevel; }
    public void setGlucoseLevel(Double glucoseLevel) { this.glucoseLevel = glucoseLevel; }

    public LocalDateTime getMeasurementTime() { return measurementTime; }
    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = (measurementTime != null) ? measurementTime.truncatedTo(ChronoUnit.SECONDS) : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public String getMeasurementSource() { return measurementSource; }
    public void setMeasurementSource(String measurementSource) { this.measurementSource = measurementSource; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Equals, HashCode, and ToString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlucoseReading that = (GlucoseReading) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(glucoseLevel, that.glucoseLevel) &&
                Objects.equals(measurementTime, that.measurementTime) &&
                Objects.equals(measurementSource, that.measurementSource) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, glucoseLevel, measurementTime, measurementSource, notes);
    }

    @Override
    public String toString() {
        return "GlucoseReading{" +
                "id=" + id +
                ", user=" + user +
                ", glucoseLevel=" + glucoseLevel +
                ", measurementTime=" + measurementTime +
                ", measurementSource='" + measurementSource + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}

