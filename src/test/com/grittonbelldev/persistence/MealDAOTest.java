package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.entity.FoodMealJournal;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealDAOTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<Meal> mealDAO;
    private GenericDAO<User> userDAO;
    private GenericDAO<FoodMealJournal> foodMealJournalDAO;

    @BeforeEach
    void setUp() {
        logger.info("Setting up MealDAOTest...");
        mealDAO = new GenericDAO<>(Meal.class);
        userDAO = new GenericDAO<>(User.class);
        foodMealJournalDAO = new GenericDAO<>(FoodMealJournal.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        Meal expectedMeal = new Meal();
        User expectedUser = userDAO.getById("user-003");
        expectedMeal.setId(3L);
        expectedMeal.setUser(expectedUser);
        expectedMeal.setMealName("Omega-3 Rich Dinner");
        expectedMeal.setTimeEaten(LocalDateTime.of(2024, 2, 17, 19, 0));
        expectedMeal.setFavorite(true);

        logger.info(expectedMeal.toString());
        Meal retrievedMeal = mealDAO.getById(3L);
        assertNotNull(retrievedMeal);
        assertEquals(expectedMeal, retrievedMeal);
    }

    @Test
    void insertSuccess() {
        User user = userDAO.getById("user-001");
        assertNotNull(user);

        Meal mealToInsert = new Meal(user, "Late Night Snack", LocalDateTime.now(), false);
        Meal insertedMeal = mealDAO.insert(mealToInsert);

        assertNotNull(insertedMeal);
        assertNotEquals(0, insertedMeal.getId());
        Meal retrievedMeal = mealDAO.getById(insertedMeal.getId());

        assertEquals(mealToInsert, retrievedMeal);
    }

    @Test
    void updateSuccess() {
        Meal mealToUpdate = mealDAO.getById(3);
        assertNotNull(mealToUpdate);

        mealToUpdate.setMealName("Updated Omega-3 Dinner");
        mealDAO.update(mealToUpdate);

        Meal retrievedMeal = mealDAO.getById(3);
        assertEquals("Updated Omega-3 Dinner", retrievedMeal.getMealName());
    }

    @Test
    void deleteMealAndCheckFoodMealJournalRemains() {
        Meal mealToDelete = mealDAO.getById(3);
        assertNotNull(mealToDelete);

        List<FoodMealJournal> relatedEntries = foodMealJournalDAO.getByPropertyEqual("meal", mealToDelete);
        assertFalse(relatedEntries.isEmpty(), "Meal should have linked food items before deletion");

        mealDAO.delete(mealToDelete);
        assertNull(mealDAO.getById(3));

        List<FoodMealJournal> entriesAfterDeletion = foodMealJournalDAO.getByPropertyEqual("meal", mealToDelete);
        assertTrue(entriesAfterDeletion.isEmpty(), "FoodMealJournal entries should be deleted when meal is deleted");
    }

    @Test
    void getAllSuccess() {
        List<Meal> meals = mealDAO.getAll();
        assertEquals(3, meals.size());
    }

    @Test
    void getByPropertyEqual() {
        List<Meal> meals = mealDAO.getByPropertyEqual("mealName", "Healthy Breakfast");
        assertEquals(1, meals.size());

        Meal expectedMeal = new Meal();
        User expectedUser = userDAO.getById("user-001");
        expectedMeal.setId(1L);
        expectedMeal.setUser(expectedUser);
        expectedMeal.setMealName("Healthy Breakfast");
        expectedMeal.setTimeEaten(LocalDateTime.of(2024, 2, 17, 8, 0));
        expectedMeal.setFavorite(true);

        assertEquals(expectedMeal, meals.get(0));
    }

    @Test
    void getByPropertyLike() {
        List<Meal> meals = mealDAO.getByPropertyLike("mealName", "Dinner");

        Meal expectedMeal = new Meal();
        expectedMeal.setId(3L);
        expectedMeal.setMealName("Omega-3 Rich Dinner");

        assertEquals(1, meals.size());
        assertEquals(expectedMeal.getMealName(), meals.get(0).getMealName());
    }
}
