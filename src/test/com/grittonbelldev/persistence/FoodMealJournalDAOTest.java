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
import java.util.Optional;

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
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        FoodMealJournal expectedEntry = new FoodMealJournal();
        expectedEntry.setId(1L);
        expectedEntry.setServingSize(1.5);

        FoodMealJournal retrievedEntry = foodMealJournalDAO.getById(1L);
        assertNotNull(retrievedEntry);
        assertEquals(expectedEntry.getId(), retrievedEntry.getId());
        assertEquals(expectedEntry.getServingSize(), retrievedEntry.getServingSize());
    }

    @Test
    void insertSuccess() {
        Meal meal = mealDAO.getById(1);
        Food food = foodDAO.getById(3);
        assertNotNull(meal);
        assertNotNull(food);

        FoodMealJournal newEntry = new FoodMealJournal(meal, food, 2.0);
        FoodMealJournal insertedEntry = foodMealJournalDAO.insert(newEntry);

        assertNotNull(insertedEntry);
        assertNotEquals(0, insertedEntry.getId());

        FoodMealJournal retrievedEntry = foodMealJournalDAO.getById(insertedEntry.getId());
        assertEquals(newEntry, retrievedEntry);
    }

    @Test
    void updateSuccess() {
        FoodMealJournal entryToUpdate = foodMealJournalDAO.getById(1);
        assertNotNull(entryToUpdate);

        entryToUpdate.setServingSize(2.0);
        foodMealJournalDAO.update(entryToUpdate);

        FoodMealJournal retrievedEntry = foodMealJournalDAO.getById(1);

        //junit has two versions of assertEquals() 1 for objects and 1 for floating-point values
        //So we can't use assertEquals(2.0, retrievedEntry.getServingSize()); because it can't infer which one to use.
        //We have to use a assertEquals(double expected, double actual, double delta) so it knows to
        // handle the floating point precision issues.
        // In our case delta will allow for small rounding differences up to 0.001
        assertEquals(2.0, retrievedEntry.getServingSize(), 0.001);
    }

    @Test
    void deleteEntry() {
        FoodMealJournal entryToDelete = foodMealJournalDAO.getById(1);
        assertNotNull(entryToDelete);

        foodMealJournalDAO.delete(entryToDelete);
        assertNull(foodMealJournalDAO.getById(1));
    }

    @Test
    void deleteMealAndCheckFoodMealJournalDeleted() {
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
    void deleteFoodAndCheckFoodMealJournalDeleted() {
        Food foodToDelete = foodDAO.getById(3);
        assertNotNull(foodToDelete);

        List<FoodMealJournal> relatedEntries = foodMealJournalDAO.getByPropertyEqual("food", foodToDelete);
        assertFalse(relatedEntries.isEmpty(), "Food should have linked meal entries before deletion");

        foodDAO.delete(foodToDelete);
        assertNull(foodDAO.getById(3));

        List<FoodMealJournal> entriesAfterDeletion = foodMealJournalDAO.getByPropertyEqual("food", foodToDelete);
        assertTrue(entriesAfterDeletion.isEmpty(), "FoodMealJournal entries should be deleted when food is deleted");
    }

    @Test
    void getAllSuccess() {
        List<FoodMealJournal> entries = foodMealJournalDAO.getAll();
        assertEquals(5, entries.size());
    }

    @Test
    void getByPropertyEqual() {
        List<FoodMealJournal> entries = foodMealJournalDAO.getByPropertyEqual("servingSize", 1.5);
        assertFalse(entries.isEmpty());
        assertEquals(2, entries.size());
    }
}

