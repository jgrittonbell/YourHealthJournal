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
 * Service layer for managing GlucoseReading entities.
 *
 * <p>This service provides user-scoped operations for creating, retrieving,
 * updating, and deleting glucose readings. It maps between entity and DTO
 * representations and validates user access to each reading.</p>
 */
public class GlucoseService {

    // DAOs for glucose readings and users
    private final GenericDAO<GlucoseReading> glucoseDao = new GenericDAO<>(GlucoseReading.class);
    private final GenericDAO<User> userDao = new GenericDAO<>(User.class);

    /**
     * Retrieves all glucose readings associated with the given user ID.
     *
     * @param userId the ID of the user
     * @return a list of GlucoseResponseDto instances
     */
    public List<GlucoseResponseDto> listAllForUser(long userId) {
        return glucoseDao.getByPropertyEqual("user.id", userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds a specific glucose reading by ID, verifying user ownership.
     *
     * @param userId the ID of the user
     * @param readingId the ID of the glucose reading
     * @return the corresponding GlucoseResponseDto
     * @throws WebApplicationException if the reading is not found or not owned by the user
     */
    public GlucoseResponseDto findForUser(long userId, long readingId) {
        GlucoseReading r = glucoseDao.getById(readingId);
        if (r == null || r.getUser() == null || r.getUser().getId() != userId) {
            throw new WebApplicationException("Reading not found", Response.Status.NOT_FOUND);
        }
        return toResponseDto(r);
    }

    /**
     * Creates a new glucose reading for the specified user.
     *
     * @param userId the ID of the user
     * @param dto the request data containing the glucose level, timestamp, source, and notes
     * @return the created reading as a GlucoseResponseDto
     * @throws WebApplicationException if the user is not found
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
     * Updates an existing glucose reading for the given user.
     *
     * <p>This method replaces all editable fields of the reading.</p>
     *
     * @param userId the ID of the user
     * @param readingId the ID of the reading to update
     * @param dto the updated reading data
     * @return the updated reading as a GlucoseResponseDto
     * @throws WebApplicationException if the reading does not exist or is not owned by the user
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
     * Deletes a glucose reading, verifying that it belongs to the user.
     *
     * @param userId the ID of the user
     * @param readingId the ID of the reading to delete
     * @throws WebApplicationException if the reading is not found or not owned by the user
     */
    public void deleteForUser(long userId, long readingId) {
        GlucoseReading r = glucoseDao.getById(readingId);
        if (r == null || r.getUser() == null || r.getUser().getId() != userId) {
            throw new WebApplicationException("Reading not found", Response.Status.NOT_FOUND);
        }
        glucoseDao.delete(r);
    }

    /**
     * Maps a GlucoseReading entity to a GlucoseResponseDto.
     *
     * @param r the GlucoseReading entity
     * @return the corresponding GlucoseResponseDto
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
