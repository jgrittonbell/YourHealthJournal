
package com.grittonbelldev.service;

import com.grittonbelldev.dto.GlucoseRequestDto;
import com.grittonbelldev.dto.GlucoseResponseDto;
import com.grittonbelldev.entity.GlucoseReading;
import com.grittonbelldev.persistence.GenericDAO;

import java.util.List;
import java.util.stream.Collectors;

public class GlucoseService {
    private final GenericDAO<GlucoseReading> glucoseDao = new GenericDAO<>(GlucoseReading.class);

    public List<GlucoseResponseDto> listAll() {
        return glucoseDao.getAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public GlucoseResponseDto find(Long id) {
        return toResponseDto(glucoseDao.getById(id));
    }

    public GlucoseResponseDto create(GlucoseRequestDto dto) {
        GlucoseReading r = new GlucoseReading();
        r.setGlucoseLevel(dto.getGlucoseLevel());
        r.setMeasurementTime(dto.getMeasurementTime());
        r.setMeasurementSource(dto.getMeasurementSource());
        r.setNotes(dto.getNotes());
        glucoseDao.insert(r);
        return toResponseDto(r);
    }

    public GlucoseResponseDto update(Long id, GlucoseRequestDto dto) {
        GlucoseReading r = glucoseDao.getById(id);
        r.setGlucoseLevel(dto.getGlucoseLevel());
        r.setMeasurementTime(dto.getMeasurementTime());
        r.setMeasurementSource(dto.getMeasurementSource());
        r.setNotes(dto.getNotes());
        glucoseDao.update(r);
        return toResponseDto(r);
    }

    public void delete(Long id) {
        GlucoseReading r = glucoseDao.getById(id);
        glucoseDao.delete(r);
    }

    private GlucoseResponseDto toResponseDto(GlucoseReading r) {
        GlucoseResponseDto d = new GlucoseResponseDto();
        d.setId(r.getId());
        d.setGlucoseLevel(r.getGlucoseLevel());
        d.setMeasurementTime(r.getMeasurementTime());
        d.setMeasurementSource(r.getMeasurementSource());
        d.setNotes(r.getNotes());
        return d;
    }
}
