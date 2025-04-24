
package com.grittonbelldev.service;

import com.grittonbelldev.dto.GlucoseRequestDto;
import com.grittonbelldev.dto.GlucoseResponseDto;
import com.grittonbelldev.entity.GlucoseReading;
import com.grittonbelldev.persistence.GenericDAO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing GlucoseReading entities.
 * <p>
 * Provides CRUD operations for glucose readings and maps between
 * request/response DTOs and the persistence entities via GenericDAO.
 * </p>
 */
public class GlucoseService {
    /** DAO for GlucoseReading entity persistence. */
    private final GenericDAO<GlucoseReading> glucoseDao = new GenericDAO<>(GlucoseReading.class);

    /**
     * Retrieves all glucose readings as response DTOs.
     *
     * @return List of GlucoseResponseDto representing all persisted readings
     */
    public List<GlucoseResponseDto> listAll() {
        return glucoseDao.getAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single glucose reading by its identifier.
     *
     * @param id the primary key of the reading to find
     * @return GlucoseResponseDto representing the found reading
     * @throws IllegalArgumentException if no reading is found for the given id
     */
    public GlucoseResponseDto find(Long id) {
        GlucoseReading reading = glucoseDao.getById(id);
        if (reading == null) {
            throw new IllegalArgumentException("Glucose reading not found for id " + id);
        }
        return toResponseDto(reading);
    }

    /**
     * Creates and persists a new glucose reading based on the provided DTO.
     *
     * @param dto GlucoseRequestDto containing the measurement data
     * @return GlucoseResponseDto for the newly created reading
     */
    public GlucoseResponseDto create(GlucoseRequestDto dto) {
        // Map DTO to entity
        GlucoseReading reading = new GlucoseReading();
        reading.setGlucoseLevel(dto.getGlucoseLevel());
        reading.setMeasurementTime(dto.getMeasurementTime());
        reading.setMeasurementSource(dto.getMeasurementSource());
        reading.setNotes(dto.getNotes());
        // Persist entity
        glucoseDao.insert(reading);
        // Convert back to DTO
        return toResponseDto(reading);
    }

    /**
     * Updates an existing glucose reading with values from the provided DTO.
     *
     * @param id  identifier of the reading to update
     * @param dto GlucoseRequestDto containing updated fields
     * @return GlucoseResponseDto for the updated reading
     * @throws IllegalArgumentException if no reading is found for the given id
     */
    public GlucoseResponseDto update(Long id, GlucoseRequestDto dto) {
        GlucoseReading reading = glucoseDao.getById(id);
        if (reading == null) {
            throw new IllegalArgumentException("Cannot update; glucose reading not found for id " + id);
        }
        // Apply updates
        reading.setGlucoseLevel(dto.getGlucoseLevel());
        reading.setMeasurementTime(dto.getMeasurementTime());
        reading.setMeasurementSource(dto.getMeasurementSource());
        reading.setNotes(dto.getNotes());
        // Persist changes
        glucoseDao.update(reading);
        return toResponseDto(reading);
    }

    /**
     * Deletes a glucose reading by its identifier.
     *
     * @param id identifier of the reading to delete
     * @throws IllegalArgumentException if no reading is found for the given id
     */
    public void delete(Long id) {
        GlucoseReading reading = glucoseDao.getById(id);
        if (reading == null) {
            throw new IllegalArgumentException("Cannot delete; glucose reading not found for id " + id);
        }
        glucoseDao.delete(reading);
    }

    /**
     * Converts a GlucoseReading entity to its corresponding response DTO.
     *
     * @param reading the entity to convert
     * @return GlucoseResponseDto populated with entity data
     */
    private GlucoseResponseDto toResponseDto(GlucoseReading reading) {
        GlucoseResponseDto dto = new GlucoseResponseDto();
        dto.setId(reading.getId());
        dto.setGlucoseLevel(reading.getGlucoseLevel());
        dto.setMeasurementTime(reading.getMeasurementTime());
        dto.setMeasurementSource(reading.getMeasurementSource());
        dto.setNotes(reading.getNotes());
        return dto;
    }
}

