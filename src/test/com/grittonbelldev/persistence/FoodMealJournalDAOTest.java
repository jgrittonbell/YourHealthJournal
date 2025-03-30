package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.FoodMealJournal;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodMealJournalDAOTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<FoodMealJournal> foodMealJournalDAO;
    private GenericDAO<Meal> mealDAO;
    private GenericDAO<Food> foodDAO;

    @BeforeEach
    void setUp() {
        logger.info("Setting up FoodMealJournalDAOTest...");
        foodMealJournalDAO = new GenericDAO<>(FoodMealJournal.class);
        mealDAO = new GenericDAO<>(Meal.class);
        foodDAO = new GenericDAO<>(Food.class);
        Database.getInstance().runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        FoodMealJournal journal = foodMealJournalDAO.getById(1L);
        assertNotNull(journal);
        assertEquals(1.5, journal.getServingSize(), 0.001);
    }

    @Test
    void insertSuccess() {
        Meal meal = mealDAO.getById(1L);
        Food food = foodDAO.getById(3L);
        assertNotNull(meal);
        assertNotNull(food);

        FoodMealJournal newEntry = new FoodMealJournal(meal, food, 2.0);
        FoodMealJournal inserted = foodMealJournalDAO.insert(newEntry);

        assertNotNull(inserted.getId());
        FoodMealJournal retrieved = foodMealJournalDAO.getById(inserted.getId());
        assertEquals(inserted, retrieved);
    }

    @Test
    void updateSuccess() {
        FoodMealJournal entry = foodMealJournalDAO.getById(1L);
        assertNotNull(entry);
        entry.setServingSize(2.0);

        foodMealJournalDAO.update(entry);
        FoodMealJournal updated = foodMealJournalDAO.getById(1L);
        assertEquals(2.0, updated.getServingSize(), 0.001);
    }

    @Test
    void deleteSuccess() {
        FoodMealJournal entry = foodMealJournalDAO.getById(1L);
        assertNotNull(entry);

        foodMealJournalDAO.delete(entry);
        assertNull(foodMealJournalDAO.getById(1L));
    }

    @Test
    void deleteMealCascadesJournal() {
        Meal meal = mealDAO.getById(3L);
        assertNotNull(meal);

        List<FoodMealJournal> before = foodMealJournalDAO.getByPropertyEqual("meal", meal);
        assertFalse(before.isEmpty());

        mealDAO.delete(meal);

        List<FoodMealJournal> after = foodMealJournalDAO.getByPropertyEqual("meal", meal);
        assertTrue(after.isEmpty());
    }

    @Test
    void deleteFoodCascadesJournal() {
        Food food = foodDAO.getById(3L);
        assertNotNull(food);

        List<FoodMealJournal> before = foodMealJournalDAO.getByPropertyEqual("food", food);
        assertFalse(before.isEmpty());

        foodDAO.delete(food);

        List<FoodMealJournal> after = foodMealJournalDAO.getByPropertyEqual("food", food);
        assertTrue(after.isEmpty());
    }

    @Test
    void getAllSuccess() {
        List<FoodMealJournal> all = foodMealJournalDAO.getAll();
        assertEquals(5, all.size());
    }

    @Test
    void getByPropertyEqualSuccess() {
        List<FoodMealJournal> matching = foodMealJournalDAO.getByPropertyEqual("servingSize", 1.5);
        assertEquals(2, matching.size());
    }
}
