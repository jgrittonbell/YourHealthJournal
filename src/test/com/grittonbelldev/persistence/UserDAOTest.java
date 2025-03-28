package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<User> userDAO;
    private GenericDAO<Meal> mealDAO;
    private GenericDAO<Food> foodDAO;

    @BeforeEach
    void setUp() {
        logger.info("Log4j2 is working! This should appear in log files.");
        userDAO = new GenericDAO<>(User.class);
        mealDAO = new GenericDAO<>(Meal.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        foodDAO = new GenericDAO<>(Food.class);
    }

    @Test
    void getByIdSuccess() {
        User expectedUser = new User("John Doe", "john.doe@example.com", null);
        expectedUser.setId(1L);

        User retrievedUser = userDAO.getById(1L);
        assertNotNull(retrievedUser);
        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    void updateSuccess() {
        User userToUpdate = userDAO.getById(1);
        userToUpdate.setFullName("Johnathan Doe");
        userDAO.update(userToUpdate);

        User retrievedUser = userDAO.getById(1);
        assertEquals("Johnathan Doe", retrievedUser.getFullName());
    }

    @Test
    void insertSuccess() {
        User userToInsert = new User("Michael Anderson", "michael.anderson@example.com",
                LocalDateTime.of(2024, 2, 18, 9, 15, 0));

        User insertedUser = userDAO.insert(userToInsert);
        assertNotNull(insertedUser);
        assertNotEquals(0, insertedUser.getId()); // Ensure ID is assigned

        User retrievedUserFromInsert = userDAO.getById(insertedUser.getId());
        assertEquals(userToInsert, retrievedUserFromInsert);
    }

    @Test
    void deleteSuccess() {
        User userToDelete = userDAO.getById(3);
        assertNotNull(userToDelete);
        userDAO.delete(userToDelete);
        assertNull(userDAO.getById(3));
    }

    @Test
    void deleteUserAndCheckMealsDeletedButFoodsRemain() {
        // Step 1: Retrieve an existing user (User ID 1 - John Doe)
        User userToDelete = userDAO.getById(1);
        assertNotNull(userToDelete);

        // Step 2: Verify meals exist for this user before deletion
        List<Meal> userMeals = mealDAO.getByPropertyEqual("user", userToDelete);
        assertFalse(userMeals.isEmpty(), "User should have meals before deletion");

        // Step 3: Delete the user
        userDAO.delete(userToDelete);

        // Step 4: Verify the user no longer exists
        assertNull(userDAO.getById(1));

        // Step 5: Verify the meals linked to this user are also deleted
        List<Meal> mealsAfterDeletion = mealDAO.getByPropertyEqual("user", userToDelete);
        assertTrue(mealsAfterDeletion.isEmpty(), "Meals should be deleted when user is deleted");

        // Step 6: Ensure food items remain in the database (they should not be deleted)
        List<Food> allFoods = foodDAO.getAll();
        assertFalse(allFoods.isEmpty(), "Food items should remain in the database");
    }

    @Test
    void getAll() {
        List<User> users = userDAO.getAll();
        assertEquals(3, users.size()); // Updated to match new dataset
    }

    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = userDAO.getByPropertyEqual("email", "jane.smith@example.com");
        assertEquals(1, users.size());

        User expectedUser = new User("Jane Smith", "jane.smith@example.com", null);
        expectedUser.setId(2L);

        assertEquals(expectedUser, users.get(0));
    }

    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = userDAO.getByPropertyLike("fullName", "John");

        User expectedUser = new User("John Doe", "john.doe@example.com", null);
        expectedUser.setId(1L);

        assertEquals(1, users.size());
        assertEquals(expectedUser, users.get(0));
    }
}