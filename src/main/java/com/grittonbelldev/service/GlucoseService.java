package com.grittonbelldev.service;

import com.grittonbelldev.dto.GlucoseRequestDto;
import com.grittonbelldev.dto.GlucoseResponseDto;
import com.grittonbelldev.entity.GlucoseReading;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing GlucoseReading entities, scoped per user.
 */
public class GlucoseService {
    private final GenericDAO<GlucoseReading> glucoseDao = new GenericDAO<>(GlucoseReading.class);
    private final GenericDAO<User> userDao = new GenericDAO<>(User.class);

    /**
     * List all glucose readings belonging to the given user.
     */
    public List<GlucoseResponseDto> listAllForUser(long userId) {
        return glucoseDao.getByPropertyEqual("user.id", userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Find one reading by ID, but only if it belongs to the given user.
     */
    public GlucoseResponseDto findForUser(long userId, long readingId) {
        GlucoseReading r = glucoseDao.getById(readingId);
        if (r == null || r.getUser() == null || r.getUser().getId() != userId) {
            throw new WebApplicationException("Reading not found", Response.Status.NOT_FOUND);
        }
        return toResponseDto(r);
    }

    /**
     * Create a new reading owned by the given user.
     */
    public GlucoseResponseDto createForUser(long userId, GlucoseRequestDto dto) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }
        GlucoseReading r = new GlucoseReading();
        r.setUser(user);
        r.setGlucoseLevel(dto.getGlucoseLevel());
        r.setMeasurementTime(dto.getMeasurementTime());
        r.setMeasurementSource(dto.getMeasurementSource());
        r.setNotes(dto.getNotes());
        glucoseDao.insert(r);
        return toResponseDto(r);
    }

    /**
     * Update an existing reading, only if it belongs to the given user.
     */
    public GlucoseResponseDto updateForUser(long userId, long readingId, GlucoseRequestDto dto) {
        GlucoseReading r = glucoseDao.getById(readingId);
        if (r == null || r.getUser() == null || r.getUser().getId() != userId) {
            throw new WebApplicationException("Reading not found", Response.Status.NOT_FOUND);
        }
        r.setGlucoseLevel(dto.getGlucoseLevel());
        r.setMeasurementTime(dto.getMeasurementTime());
        r.setMeasurementSource(dto.getMeasurementSource());
        r.setNotes(dto.getNotes());
        glucoseDao.update(r);
        return toResponseDto(r);
    }

    /**
     * Delete a reading, only if it belongs to the given user.
     */
    public void deleteForUser(long userId, long readingId) {
        GlucoseReading r = glucoseDao.getById(readingId);
        if (r == null || r.getUser() == null || r.getUser().getId() != userId) {
            throw new WebApplicationException("Reading not found", Response.Status.NOT_FOUND);
        }
        glucoseDao.delete(r);
    }

    /**
     * Convert entity → response DTO.
     */
    private GlucoseResponseDto toResponseDto(GlucoseReading r) {
        GlucoseResponseDto dto = new GlucoseResponseDto();
        dto.setId(r.getId());
        dto.setGlucoseLevel(r.getGlucoseLevel());
        dto.setMeasurementTime(r.getMeasurementTime());
        dto.setMeasurementSource(r.getMeasurementSource());
        dto.setNotes(r.getNotes());
        return dto;
    }
}
