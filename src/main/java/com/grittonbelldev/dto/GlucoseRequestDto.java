package com.grittonbelldev.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * Request DTO for creating or updating a glucose reading.
 * <p>
 * Users supply measurement data including value, time, source, and optional notes.
 * </p>
 */
public class GlucoseRequestDto {
    /**
     * Blood glucose level in mg/dL.
     * Must not be null.
     */
    @NotNull
    private Double glucoseLevel;

    /**
     * Timestamp when the measurement was taken.
     * Must not be null.
     */
    @NotNull
    private LocalDateTime measurementTime;

    /**
     * Source of the measurement (e.g., "Manual", "Dexcom", "Nightscout").
     * Must not be null or empty.
     */
    @NotNull
    private String measurementSource;

    /**
     * Optional notes or comments about the reading.
     */
    private String notes;

    // --- Getters and Setters ---

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