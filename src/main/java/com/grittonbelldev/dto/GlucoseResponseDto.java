package com.grittonbelldev.dto;

import java.time.LocalDateTime;

/**
 * Response DTO representing a persisted glucose reading.
 * <p>
 * Includes the database-assigned ID along with measurement details and notes.
 * </p>
 */
public class GlucoseResponseDto {
    /**
     * Unique identifier of the glucose reading.
     */
    private Long id;

    /**
     * Blood glucose level in mg/dL.
     */
    private Double glucoseLevel;

    /**
     * Timestamp when the measurement was taken.
     */
    private LocalDateTime measurementTime;

    /**
     * Source of the measurement (e.g., "Manual", "Dexcom", "Nightscout").
     */
    private String measurementSource;

    /**
     * Optional notes or comments stored with the reading.
     */
    private String notes;

    // --- Getters and Setters ---

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets glucose level.
     *
     * @return the glucose level
     */
    public Double getGlucoseLevel() {
        return glucoseLevel;
    }

    /**
     * Sets glucose level.
     *
     * @param glucoseLevel the glucose level
     */
    public void setGlucoseLevel(Double glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }

    /**
     * Gets measurement time.
     *
     * @return the measurement time
     */
    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    /**
     * Sets measurement time.
     *
     * @param measurementTime the measurement time
     */
    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = measurementTime;
    }

    /**
     * Gets measurement source.
     *
     * @return the measurement source
     */
    public String getMeasurementSource() {
        return measurementSource;
    }

    /**
     * Sets measurement source.
     *
     * @param measurementSource the measurement source
     */
    public void setMeasurementSource(String measurementSource) {
        this.measurementSource = measurementSource;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}

