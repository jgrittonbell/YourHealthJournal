package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class FoodDAOTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<Food> foodDAO;
    private GenericDAO<User> userDAO;

    @BeforeEach
    void setUp() {
        logger.info("Log4j2 is working! This should appear in log files.");
        foodDAO = new GenericDAO<>(Food.class);
        userDAO = new GenericDAO<>(User.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");

    }

    @Test
    void getByIdSuccess() {
        //Create the expected User
        User expectedUser = userDAO.getById(4);

        //Create the expected resulting food entity
        Food expectedFood = new Food();
        expectedFood.setId(4L);
        expectedFood.setUser(expectedUser);
        expectedFood.setFoodName("Cheeseburger");
        expectedFood.setTimeEaten(LocalDateTime.of(2024, 2, 16, 19, 30, 0));
        expectedFood.setMealCategory("dinner");
        expectedFood.setFat(18.00);
        expectedFood.setProtein(22.00);
        expectedFood.setCarbs(34.00);
        expectedFood.setCalories(450.00);
        expectedFood.setCholesterol(65.00);
        expectedFood.setSodium(780.00);
        expectedFood.setFiber(3.00);
        expectedFood.setSugar(5.00);
        expectedFood.setAddedSugar(2.00);
        expectedFood.setVitaminD(0.50);
        expectedFood.setCalcium(100.00);
        expectedFood.setIron(2.50);
        expectedFood.setPotassium(250.00);
        expectedFood.setNotes("Cheat meal, high in calories");

        Food retrievedFood = foodDAO.getById(4L);
        assertNotNull(retrievedFood);
        assertEquals(expectedFood, retrievedFood);
    }

    @Test
    void updateSuccess() {
        Food foodToUpdate = foodDAO.getById(4);
        foodToUpdate.setFoodName("Hamburger");
        foodDAO.update(foodToUpdate);

        Food retrievedFood = foodDAO.getById(4);
        assertEquals("Hamburger", retrievedFood.getFoodName());

    }

    @Test
    void insert() {
        //Create user to assocate with food
        User insertedUser = userDAO.getById(4);
        Food foodToInsert = new Food();
        foodToInsert.setUser(insertedUser);
        foodToInsert.setFoodName("Egg White Omelette");
        foodToInsert.setTimeEaten(LocalDateTime.of(2024, 2, 16, 19, 30, 0));
        foodToInsert.setMealCategory("breakfast");
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
    void delete() {
        Food foodToDelete = foodDAO.getById(4);
        assertNotNull(foodToDelete);
        foodDAO.delete(foodToDelete);
        assertNull(foodDAO.getById(4));
    }



    @Test
    void getAll() {
        List<Food> foods = foodDAO.getAll();
        assertEquals(10, foods.size());

    }

    @Test
    void getByPropertyEqual() {
        List<Food> foods = foodDAO.getByPropertyEqual("mealCategory", "dinner");
        assertEquals(3, foods.size());

        User insertedUser = userDAO.getById(4);
        //Create the expected resulting food entity
        Food expectedFood = new Food();
        expectedFood.setId(4L);
        expectedFood.setUser(insertedUser);
        expectedFood.setFoodName("Cheeseburger");
        expectedFood.setTimeEaten(LocalDateTime.of(2024, 2, 16, 19, 30, 0));
        expectedFood.setMealCategory("dinner");
        expectedFood.setFat(18.00);
        expectedFood.setProtein(22.00);
        expectedFood.setCarbs(34.00);
        expectedFood.setCalories(450.00);
        expectedFood.setCholesterol(65.00);
        expectedFood.setSodium(780.00);
        expectedFood.setFiber(3.00);
        expectedFood.setSugar(5.00);
        expectedFood.setAddedSugar(2.00);
        expectedFood.setVitaminD(0.50);
        expectedFood.setCalcium(100.00);
        expectedFood.setIron(2.50);
        expectedFood.setPotassium(250.00);
        expectedFood.setNotes("Cheat meal, high in calories");

        assertTrue(foods.contains(expectedFood));
    }

    @Test
    void getByPropertyLike() {
        List<Food> foods = foodDAO.getByPropertyLike("notes", "protein" );

        User insertedUser = userDAO.getById(10);
        //Create the expected resulting food entity
        Food expectedFood = new Food();
        expectedFood.setId(10L);
        expectedFood.setUser(insertedUser);
        expectedFood.setFoodName("Salmon with Steamed Broccoli");
        expectedFood.setTimeEaten(LocalDateTime.of(2024, 2, 13, 19, 0, 0));
        expectedFood.setMealCategory("dinner");
        expectedFood.setFat(15.00);
        expectedFood.setProtein(35.00);
        expectedFood.setCarbs(10.00);
        expectedFood.setCalories(380.00);
        expectedFood.setCholesterol(80.00);
        expectedFood.setSodium(400.00);
        expectedFood.setFiber(5.00); expectedFood.setSugar(1.00);
        expectedFood.setAddedSugar(null);
        expectedFood.setVitaminD(2.00); expectedFood.setCalcium(180.00); expectedFood.setIron(1.80); expectedFood.setPotassium(400.00);
        expectedFood.setNotes("High-protein dinner with omega-3");

        assertEquals(foods.get(3), expectedFood);

        assertEquals(4, foods.size());
    }

}