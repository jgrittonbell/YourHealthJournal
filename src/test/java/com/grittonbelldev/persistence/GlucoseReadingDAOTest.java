package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.GlucoseReading;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GlucoseReadingDAOTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<GlucoseReading> glucoseReadingDAO;
    private GenericDAO<User> userDAO;

    @BeforeEach
    void setUp() {
        logger.info("Setting up GlucoseReadingDAOTest...");
        glucoseReadingDAO = new GenericDAO<>(GlucoseReading.class);
        userDAO = new GenericDAO<>(User.class);
        Database.getInstance().runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        GlucoseReading reading = glucoseReadingDAO.getById(1L);
        assertNotNull(reading);
        assertEquals(110.5, reading.getGlucoseLevel(), 0.01);
        assertEquals("Manual", reading.getMeasurementSource());
    }

    @Test
    void insertSuccess() {
        User user = userDAO.getById("user-001");
        assertNotNull(user);

        GlucoseReading newReading = new GlucoseReading(user, 103.4, LocalDateTime.now(), "Dexcom", "After workout");
        GlucoseReading inserted = glucoseReadingDAO.insert(newReading);

        assertNotNull(inserted.getId());
        GlucoseReading retrieved = glucoseReadingDAO.getById(inserted.getId());
        assertEquals(inserted, retrieved);
    }

    @Test
    void updateSuccess() {
        GlucoseReading reading = glucoseReadingDAO.getById(1L);
        assertNotNull(reading);
        reading.setGlucoseLevel(130.0);

        glucoseReadingDAO.update(reading);
        GlucoseReading updated = glucoseReadingDAO.getById(1L);
        assertEquals(130.0, updated.getGlucoseLevel(), 0.01);
    }

    @Test
    void deleteSuccess() {
        GlucoseReading reading = glucoseReadingDAO.getById(1L);
        assertNotNull(reading);

        glucoseReadingDAO.delete(reading);
        assertNull(glucoseReadingDAO.getById(1L));
    }

    @Test
    void deleteUserCascadesReadings() {
        User user = userDAO.getById("user-001");
        assertNotNull(user);

        List<GlucoseReading> readingsBefore = glucoseReadingDAO.getByPropertyEqual("user", user);
        assertFalse(readingsBefore.isEmpty());

        userDAO.delete(user);

        List<GlucoseReading> readingsAfter = glucoseReadingDAO.getByPropertyEqual("user", user);
        assertTrue(readingsAfter.isEmpty());
    }

    @Test
    void getAllSuccess() {
        List<GlucoseReading> readings = glucoseReadingDAO.getAll();
        assertEquals(4, readings.size());
    }

    @Test
    void getByPropertyEqualSuccess() {
        List<GlucoseReading> manual = glucoseReadingDAO.getByPropertyEqual("measurementSource", "Manual");
        assertEquals(2, manual.size());
    }
}
