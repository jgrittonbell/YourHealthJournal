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
        foodDAO = new GenericDAO<>(Food.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        User expectedUser = new User("John", "Doe", "john.doe@example.com", null);
        expectedUser.setCognitoId("user-001");

        User retrievedUser = userDAO.getById("user-001");
        assertNotNull(retrievedUser);
        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    void updateSuccess() {
        User userToUpdate = userDAO.getById("user-001");
        userToUpdate.setFirstName("Johnny");
        userDAO.update(userToUpdate);

        User retrievedUser = userDAO.getById("user-001");
        assertEquals("Johnny", retrievedUser.getFirstName());
    }

    @Test
    void insertSuccess() {
        User userToInsert = new User("Michael", "Anderson", "michael.anderson@example.com",
                LocalDateTime.of(2024, 2, 18, 9, 15, 0));
        userToInsert.setCognitoId("user-999");

        User insertedUser = userDAO.insert(userToInsert);
        assertNotNull(insertedUser);
        assertEquals("user-999", insertedUser.getCognitoId());

        User retrievedUserFromInsert = userDAO.getById("user-999");
        assertEquals(userToInsert, retrievedUserFromInsert);
    }

    @Test
    void deleteSuccess() {
        User userToDelete = userDAO.getById("user-003");
        assertNotNull(userToDelete);
        userDAO.delete(userToDelete);
        assertNull(userDAO.getById("user-003"));
    }

    @Test
    void deleteUserAndCheckMealsDeletedButFoodsRemain() {
        User userToDelete = userDAO.getById("user-001");
        assertNotNull(userToDelete);

        List<Meal> userMeals = mealDAO.getByPropertyEqual("user", userToDelete);
        assertFalse(userMeals.isEmpty(), "User should have meals before deletion");

        userDAO.delete(userToDelete);

        assertNull(userDAO.getById("user-001"));

        List<Meal> mealsAfterDeletion = mealDAO.getByPropertyEqual("user", userToDelete);
        assertTrue(mealsAfterDeletion.isEmpty(), "Meals should be deleted when user is deleted");

        List<Food> allFoods = foodDAO.getAll();
        assertFalse(allFoods.isEmpty(), "Food items should remain in the database");
    }

    @Test
    void getAll() {
        List<User> users = userDAO.getAll();
        assertEquals(3, users.size()); // Assumes 3 users are seeded
    }

    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = userDAO.getByPropertyEqual("email", "jane.smith@example.com");
        assertEquals(1, users.size());

        User expectedUser = new User("Jane", "Smith", "jane.smith@example.com", null);
        expectedUser.setCognitoId("user-002");

        assertEquals(expectedUser, users.get(0));
    }

    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = userDAO.getByPropertyLike("firstName", "John");

        User expectedUser = new User("John", "Doe", "john.doe@example.com", null);
        expectedUser.setCognitoId("user-001");

        assertEquals(1, users.size());
        assertEquals(expectedUser, users.get(0));
    }
}
