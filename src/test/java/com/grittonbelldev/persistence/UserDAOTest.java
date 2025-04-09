package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.util.Database;
import com.grittonbelldev.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<User> userDAO;
    private GenericDAO<Meal> mealDAO;
    private GenericDAO<Food> foodDAO;

    @BeforeEach
    void setUp() throws SQLException {
        Database.getInstance().runSQL("cleanDB.sql");// Reset the DB schema + seed data
        
        userDAO = new GenericDAO<>(User.class);
        mealDAO = new GenericDAO<>(Meal.class);
        foodDAO = new GenericDAO<>(Food.class);
    }

    
    @Test
    void getByIdSuccess() {
        User retrievedUser = userDAO.getById(1L);
        assertNotNull(retrievedUser);
        assertEquals("user-001", retrievedUser.getCognitoId());
    }

    @Test
    void updateSuccess() {
        User userToUpdate = userDAO.getById(1L);
        userToUpdate.setFirstName("Johnny");
        userDAO.update(userToUpdate);

        User retrievedUser = userDAO.getById(1L);
        assertEquals("Johnny", retrievedUser.getFirstName());
    }

    @Test
    void insertSuccess() {
        User userToInsert = new User("user-999", "Michael", "Anderson", "michael.anderson@example.com");
        User insertedUser = userDAO.insert(userToInsert);
        assertNotNull(insertedUser.getId());

        User retrievedUser = userDAO.getById(insertedUser.getId());
        assertEquals("user-999", retrievedUser.getCognitoId());
    }

    @Test
    void deleteSuccess() {
        User userToDelete = userDAO.getById(3L);
        assertNotNull(userToDelete);
        userDAO.delete(userToDelete);
        assertNull(userDAO.getById(3L));
    }

    @Test
    void deleteUserAndCheckMealsDeletedButFoodsRemain() {
        User userToDelete = userDAO.getById(1L);
        assertNotNull(userToDelete);

        List<Meal> userMeals = mealDAO.getByPropertyEqual("user", userToDelete);
        assertFalse(userMeals.isEmpty());

        userDAO.delete(userToDelete);

        assertNull(userDAO.getById(1L));

        List<Meal> mealsAfterDeletion = mealDAO.getByPropertyEqual("user", userToDelete);
        assertTrue(mealsAfterDeletion.isEmpty());

        List<Food> allFoods = foodDAO.getAll();
        assertFalse(allFoods.isEmpty());
    }

    @Test
    void getAll() {
        List<User> users = userDAO.getAll();
        assertEquals(3, users.size());
    }

    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = userDAO.getByPropertyEqual("email", "jane.smith@example.com");
        assertEquals(1, users.size());
        assertEquals("user-002", users.get(0).getCognitoId());
    }

    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = userDAO.getByPropertyLike("firstName", "John");
        assertEquals(1, users.size());
        assertEquals("user-001", users.get(0).getCognitoId());
    }
}
