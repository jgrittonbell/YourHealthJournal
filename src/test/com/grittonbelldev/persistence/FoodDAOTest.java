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
    FoodDAO foodDAO;

    @BeforeEach
    void setUp() {
        foodDAO = new FoodDAO();
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");

    }

    @Test
    void getByIdSuccess() {
        Food retrievedFood = foodDAO.getById(4L);
        assertNotNull(retrievedFood);
        assertEquals(retrievedFood.getFoodName(), "Cheeseburger");
    }

    @Test
    void updateSuccess() {
        Food foodToUpdate = foodDAO.getById(4);
        foodToUpdate.setFoodName("Hamburger");
        foodDAO.update(foodToUpdate);

        Food retrievedFood = foodDAO.getById(4);
        assertEquals(retrievedFood.getFoodName(), "Hamburger");

    }

    @Test
    void insert() {
        // Create a new Food to be added
        Food foodToInsert = new Food();
        foodToInsert.setUserId(11L);
        foodToInsert.setFoodName("Grilled Chicken");
        foodToInsert.setFat(3.5);
        foodToInsert.setProtein(31.0);
        foodToInsert.setCarbs(0.0);
        foodToInsert.setCalories(165.0);
        long foodId = foodDAO.insert(foodToInsert);
        assertNotEquals(-1, foodId);
        Food retrievedFood = foodDAO.getById(foodId);
        assertEquals("Grilled Chicken", retrievedFood.getFoodName());
        assertEquals(3.5, retrievedFood.getFat(), 0.01);
    }

    @Test
    void delete() {
        foodDAO.delete(foodDAO.getById(4));
        assertNull(foodDAO.getById(4));
    }

    @Test
    void getAll() {
        List<Food> foods = foodDAO.getAll();
        assertEquals(10, foods.size());

    }

    @Test
    void getByPropertyEqual() {
        List<Food> foods = foodDAO.getByPropertyEqual("mealCategory", "dinner" );
        assertEquals(3, foods.size());
    }

    @Test
    void getByPropertyLike() {
        List<Food> foods = foodDAO.getByPropertyLike("notes", "protein" );
        assertEquals(4, foods.size());
    }
}