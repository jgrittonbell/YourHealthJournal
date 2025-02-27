package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<User> userDAO;
    private GenericDAO<Food> foodDAO;

    @BeforeEach
    void setUp() {
        logger.info("Log4j2 is working! This should appear in log files.");
        userDAO = new GenericDAO<>(User.class);
        foodDAO = new GenericDAO<>(Food.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        // Create the expected resulting user entity
        User expectedUser = new User();
        expectedUser.setId(4L);
        expectedUser.setFullName("Emily White");
        expectedUser.setEmail("emily.white@example.com");
        expectedUser.setCreatedAt(null);


        User retrievedUser = userDAO.getById(4L);
        assertNotNull(retrievedUser);
        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    void updateSuccess() {
        User userToUpdate = userDAO.getById(4);
        userToUpdate.setFullName("Emily Johnson");
        userDAO.update(userToUpdate);

        User retrievedUser = userDAO.getById(4);
        assertEquals("Emily Johnson", retrievedUser.getFullName());
    }

    @Test
    void insert() {
        User userToInsert = new User();
        userToInsert.setFullName("Michael Anderson");
        userToInsert.setEmail("michael.anderson@example.com");
        userToInsert.setCreatedAt(LocalDateTime.of(2024, 2, 18, 9, 15, 0));

        User insertedUser = userDAO.insert(userToInsert);
        assertNotNull(insertedUser);
        assertNotEquals(0, insertedUser.getId()); // Ensure ID is assigned
        User retrievedUserFromInsert = userDAO.getById(insertedUser.getId());

        assertEquals(userToInsert, retrievedUserFromInsert);
    }

    @Test
    void delete() {
        User userToDelete = userDAO.getById(4);
        assertNotNull(userToDelete);
        userDAO.delete(userToDelete);
        assertNull(userDAO.getById(4));
    }

    @Test
    void deleteWithFood() {
        Food foodToDelete = foodDAO.getById(4);

        User userToDelete = userDAO.getById(4);

        userDAO.delete(userToDelete);
        assertNull(userDAO.getById(4));
        assertNull(foodDAO.getById(4));


    }

    @Test
    void getAll() {
        List<User> users = userDAO.getAll();
        assertEquals(10, users.size());
    }

    @Test
    void getByPropertyEqual() {
        List<User> users = userDAO.getByPropertyEqual("email", "emily.white@example.com");
        assertEquals(1, users.size());

        // Create the expected resulting user entity
        User expectedUser = new User();
        expectedUser.setId(4L);
        expectedUser.setFullName("Emily White");
        expectedUser.setEmail("emily.white@example.com");
        expectedUser.setCreatedAt(null);

        assertEquals(expectedUser.getId(), users.get(0).getId());
        assertEquals(expectedUser.getFullName(), users.get(0).getFullName());
        assertEquals(expectedUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void getByPropertyLike() {
        List<User> users = userDAO.getByPropertyLike("fullName", "Michael");

        // Create the expected resulting user entity
        User expectedUser = new User();
        expectedUser.setId(3L);
        expectedUser.setFullName("Michael Brown");
        expectedUser.setEmail("michael.brown@example.com");
        expectedUser.setCreatedAt(LocalDateTime.of(2024, 2, 18, 9, 15, 0));

        assertEquals(1, users.size());
        assertEquals(expectedUser.getId(), users.get(0).getId());
        assertEquals(expectedUser.getFullName(), users.get(0).getFullName());
        assertEquals(expectedUser.getEmail(), users.get(0).getEmail());
    }

}