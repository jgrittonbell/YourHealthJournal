package com.grittonbelldev.dto;

import java.time.LocalDateTime;

public class GlucoseResponseDto {
    private Long id;
    private Double glucoseLevel;
    private LocalDateTime measurementTime;
    private String measurementSource;
    private String notes;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getGlucoseLevel() { return glucoseLevel; }
    public void setGlucoseLevel(Double glucoseLevel) { this.glucoseLevel = glucoseLevel; }

    public LocalDateTime getMeasurementTime() { return measurementTime; }
    public void setMeasurementTime(LocalDateTime measurementTime) { this.measurementTime = measurementTime; }

    public String getMeasurementSource() { return measurementSource; }
    public void setMeasurementSource(String measurementSource) { this.measurementSource = measurementSource; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
