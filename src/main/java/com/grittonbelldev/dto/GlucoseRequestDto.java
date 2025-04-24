package com.grittonbelldev.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public class GlucoseRequestDto {
    @NotNull
    private Double glucoseLevel;

    @NotNull
    private LocalDateTime measurementTime;

    @NotNull
    private String measurementSource;

    private String notes;

    // getters & setters
    public Double getGlucoseLevel() { return glucoseLevel; }
    public void setGlucoseLevel(Double glucoseLevel) { this.glucoseLevel = glucoseLevel; }

    public LocalDateTime getMeasurementTime() { return measurementTime; }
    public void setMeasurementTime(LocalDateTime measurementTime) { this.measurementTime = measurementTime; }

    public String getMeasurementSource() { return measurementSource; }
    public void setMeasurementSource(String measurementSource) { this.measurementSource = measurementSource; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}