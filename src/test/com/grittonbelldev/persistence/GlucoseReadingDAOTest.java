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
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        GlucoseReading expectedReading = new GlucoseReading();
        expectedReading.setId(1L);
        expectedReading.setGlucoseLevel(110.5);
        expectedReading.setMeasurementSource("Manual");

        GlucoseReading retrievedReading = glucoseReadingDAO.getById(1L);
        assertNotNull(retrievedReading);
        assertEquals(expectedReading.getId(), retrievedReading.getId());
        assertEquals(expectedReading.getGlucoseLevel(), retrievedReading.getGlucoseLevel(), 0.001);
        assertEquals(expectedReading.getMeasurementSource(), retrievedReading.getMeasurementSource());
    }

    @Test
    void insertSuccess() {
        User user = userDAO.getById(1);
        assertNotNull(user);

        GlucoseReading newReading = new GlucoseReading(user, 95.2, LocalDateTime.now(), "Manual", "Pre-lunch reading");
        GlucoseReading insertedReading = glucoseReadingDAO.insert(newReading);

        assertNotNull(insertedReading);
        assertNotEquals(0, insertedReading.getId());

        GlucoseReading retrievedReading = glucoseReadingDAO.getById(insertedReading.getId());
        assertEquals(newReading, retrievedReading);
    }

    @Test
    void updateSuccess() {
        GlucoseReading readingToUpdate = glucoseReadingDAO.getById(1);
        assertNotNull(readingToUpdate);

        readingToUpdate.setGlucoseLevel(120.0);
        glucoseReadingDAO.update(readingToUpdate);

        GlucoseReading retrievedReading = glucoseReadingDAO.getById(1);
        assertEquals(120.0, retrievedReading.getGlucoseLevel(), 0.001);
    }

    @Test
    void deleteReading() {
        GlucoseReading readingToDelete = glucoseReadingDAO.getById(1);
        assertNotNull(readingToDelete);

        glucoseReadingDAO.delete(readingToDelete);
        assertNull(glucoseReadingDAO.getById(1));
    }

    @Test
    void deleteUserAndCheckGlucoseReadingsDeleted() {
        User userToDelete = userDAO.getById(1);
        assertNotNull(userToDelete);

        List<GlucoseReading> relatedReadings = glucoseReadingDAO.getByPropertyEqual("user", userToDelete);
        assertFalse(relatedReadings.isEmpty(), "User should have glucose readings before deletion");

        userDAO.delete(userToDelete);
        assertNull(userDAO.getById(1));

        List<GlucoseReading> readingsAfterDeletion = glucoseReadingDAO.getByPropertyEqual("user", userToDelete);
        assertTrue(readingsAfterDeletion.isEmpty(), "GlucoseReadings should be deleted when user is deleted");
    }

    @Test
    void getAllSuccess() {
        List<GlucoseReading> readings = glucoseReadingDAO.getAll();
        assertEquals(4, readings.size());
    }

    @Test
    void getByPropertyEqual() {
        List<GlucoseReading> readings = glucoseReadingDAO.getByPropertyEqual("measurementSource", "Manual");
        assertFalse(readings.isEmpty());
        assertEquals(2, readings.size());
    }
}

