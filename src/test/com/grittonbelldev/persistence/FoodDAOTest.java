package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.Food;
import com.grittonbelldev.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class FoodDAOTest {

    FoodDAO foodDAO;
    @BeforeEach
    void setUp() {
        foodDAO = new FoodDAO();
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getById() {
        Food retrievedFood = foodDAO.getById(4);
        assertNotNull(retrievedFood);
        assertEquals(retrievedFood.getFoodName(), "Cheeseburger");
    }

    @Test
    void update() {
    }

    @Test
    void insert() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByPropertyEqual() {
    }

    @Test
    void getByPropertyLike() {
    }
}