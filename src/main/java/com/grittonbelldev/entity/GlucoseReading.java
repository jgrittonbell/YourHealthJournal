package com.grittonbelldev.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Entity representing a glucose reading recorded by a user.
 *
 * <p>This entity is linked to a specific {@link User} and includes information about
 * the glucose level, the time of measurement, the source of the measurement
 * (such as "manual" or "Dexcom"), and optional notes.</p>
 */
@Entity
@Table(name = "GlucoseReading")
public class GlucoseReading {

    /** Primary key for the glucose reading. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    /** Many-to-one association with the user who recorded the reading. */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** The glucose level recorded in mg/dL or equivalent. */
    @Column(name = "glucose_level", nullable = false)
    private Double glucoseLevel;

    /** The time when the glucose measurement was taken. Defaults to now. */
    @Column(name = "measurement_time", nullable = false)
    private LocalDateTime measurementTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    /** Indicates the source of the reading (e.g., "Dexcom", "Manual"). */
    @Column(name = "measurement_source", nullable = false, length = 50)
    private String measurementSource;

    /** Optional notes the user may have added with the reading. */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // ---------------- Constructors ----------------

    /** No-argument constructor for JPA. */
    public GlucoseReading() {}

    /**
     * Constructs a GlucoseReading with the provided fields.
     *
     * @param user the user who recorded the reading
     * @param glucoseLevel the glucose level value
     * @param measurementTime the timestamp of the reading
     * @param measurementSource where the reading came from (e.g., device type)
     * @param notes optional freeform text associated with the reading
     */
    public GlucoseReading(User user, Double glucoseLevel, LocalDateTime measurementTime, String measurementSource, String notes) {
        this.user = user;
        this.glucoseLevel = glucoseLevel;
        this.measurementTime = (measurementTime != null)
                ? measurementTime.truncatedTo(ChronoUnit.SECONDS)
                : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.measurementSource = measurementSource;
        this.notes = notes;
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
     * Gets glucose level.
     *
     * @return the glucose level
     */
    public Double getGlucoseLevel() { return glucoseLevel; }

    /**
     * Sets glucose level.
     *
     * @param glucoseLevel the glucose level
     */
    public void setGlucoseLevel(Double glucoseLevel) { this.glucoseLevel = glucoseLevel; }

    /**
     * Gets measurement time.
     *
     * @return the measurement time
     */
    public LocalDateTime getMeasurementTime() { return measurementTime; }

    /**
     * Sets measurement time.
     *
     * @param measurementTime the measurement time
     */
    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = (measurementTime != null)
                ? measurementTime.truncatedTo(ChronoUnit.SECONDS)
                : LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Gets measurement source.
     *
     * @return the measurement source
     */
    public String getMeasurementSource() { return measurementSource; }

    /**
     * Sets measurement source.
     *
     * @param measurementSource the measurement source
     */
    public void setMeasurementSource(String measurementSource) { this.measurementSource = measurementSource; }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() { return notes; }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) { this.notes = notes; }

    // ---------------- Object overrides ----------------

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
