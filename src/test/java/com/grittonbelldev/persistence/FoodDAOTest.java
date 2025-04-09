package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodDAOTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<Food> foodDAO;

    @BeforeEach
    void setUp() {
        //logger.error("Log4j2 is working! This should appear in log files.");
        foodDAO = new GenericDAO<>(Food.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        Food expectedFood = new Food();
        expectedFood.setId(3L);
        expectedFood.setFoodName("Salmon with Steamed Broccoli");
        expectedFood.setFat(15.00);
        expectedFood.setProtein(35.00);
        expectedFood.setCarbs(10.00);
        expectedFood.setCalories(380.00);
        expectedFood.setCholesterol(80.00);
        expectedFood.setSodium(400.00);
        expectedFood.setFiber(5.00);
        expectedFood.setSugar(1.00);
        expectedFood.setAddedSugar(null);
        expectedFood.setVitaminD(2.00);
        expectedFood.setCalcium(180.00);
        expectedFood.setIron(1.80);
        expectedFood.setPotassium(400.00);
        expectedFood.setNotes("High-protein dinner with omega-3");

        Food retrievedFood = foodDAO.getById(3L);
        assertNotNull(retrievedFood);
        assertEquals(expectedFood, retrievedFood);
    }

    @Test
    void updateSuccess() {
        Food foodToUpdate = foodDAO.getById(3);
        foodToUpdate.setFoodName("Baked Salmon");
        foodDAO.update(foodToUpdate);

        Food retrievedFood = foodDAO.getById(3);
        assertEquals("Baked Salmon", retrievedFood.getFoodName());
    }

    @Test
    void insertSuccess() {
        Food foodToInsert = new Food();
        foodToInsert.setFoodName("Egg White Omelette");
        foodToInsert.setFat(5.0);
        foodToInsert.setProtein(20.0);
        foodToInsert.setCarbs(2.0);
        foodToInsert.setCalories(120.0);
        foodToInsert.setNotes("Low-calorie breakfast option");

        Food insertedFood = foodDAO.insert(foodToInsert);
        assertNotNull(insertedFood);
        assertNotEquals(0, insertedFood.getId()); // Ensure ID is assigned
        Food retrievedFoodFromInsert = foodDAO.getById(insertedFood.getId());

        assertEquals(foodToInsert, retrievedFoodFromInsert);
    }

    @Test
    void deleteSuccess() {
        Food foodToDelete = foodDAO.getById(3);
        assertNotNull(foodToDelete);
        foodDAO.delete(foodToDelete);
        assertNull(foodDAO.getById(3));
    }

    @Test
    void getAll() {
        List<Food> foods = foodDAO.getAll();
        assertEquals(5, foods.size());  // Updated to match the new dataset
    }

    @Test
    void getByPropertyEqualSuccess() {
        List<Food> foods = foodDAO.getByPropertyEqual("foodName", "Salmon with Steamed Broccoli");
        assertEquals(1, foods.size());

        Food expectedFood = new Food();
        expectedFood.setId(3L);
        expectedFood.setFoodName("Salmon with Steamed Broccoli");
        expectedFood.setFat(15.00);
        expectedFood.setProtein(35.00);
        expectedFood.setCarbs(10.00);
        expectedFood.setCalories(380.00);
        expectedFood.setCholesterol(80.00);
        expectedFood.setSodium(400.00);
        expectedFood.setFiber(5.00);
        expectedFood.setSugar(1.00);
        expectedFood.setAddedSugar(null);
        expectedFood.setVitaminD(2.00);
        expectedFood.setCalcium(180.00);
        expectedFood.setIron(1.80);
        expectedFood.setPotassium(400.00);
        expectedFood.setNotes("High-protein dinner with omega-3");

        assertTrue(foods.contains(expectedFood));
    }

    @Test
    void getByPropertyLikeSuccess() {
        List<Food> foods = foodDAO.getByPropertyLike("notes", "protein");

        // Foods expected to be found with "protein" in the notes
        String[] expectedFoodNames = {"Grilled Chicken Breast", "Salmon with Steamed Broccoli", "Greek Yogurt with Honey"};
        assertEquals(3, foods.size());

        for (Food food : foods) {
            assertTrue(List.of(expectedFoodNames).contains(food.getFoodName()));
        }
    }
}