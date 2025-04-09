package com.grittonbelldev.persistence;

import com.grittonbelldev.entity.FavoriteItem;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteItemDAOTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDAO<FavoriteItem> favoriteItemDAO;
    private GenericDAO<User> userDAO;
    private GenericDAO<Meal> mealDAO;
    private GenericDAO<Food> foodDAO;

    @BeforeEach
    void setUp() {
        logger.info("Setting up FavoriteItemDAOTest...");
        favoriteItemDAO = new GenericDAO<>(FavoriteItem.class);
        userDAO = new GenericDAO<>(User.class);
        mealDAO = new GenericDAO<>(Meal.class);
        foodDAO = new GenericDAO<>(Food.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getByIdSuccess() {
        FavoriteItem expectedItem = new FavoriteItem();
        expectedItem.setId(1L);
        expectedItem.setFavorite(true);

        FavoriteItem retrievedItem = favoriteItemDAO.getById(1L);
        assertNotNull(retrievedItem);
        assertEquals(expectedItem.getId(), retrievedItem.getId());
        assertEquals(expectedItem.isFavorite(), retrievedItem.isFavorite());
    }

    @Test
    void insertSuccess() {
        User user = userDAO.getById("user-001");
        Meal meal = mealDAO.getById(2);
        assertNotNull(user);
        assertNotNull(meal);

        FavoriteItem newFavorite = new FavoriteItem(user, meal, null, true);
        FavoriteItem insertedItem = favoriteItemDAO.insert(newFavorite);

        assertNotNull(insertedItem);
        assertNotEquals(0, insertedItem.getId());

        FavoriteItem retrievedItem = favoriteItemDAO.getById(insertedItem.getId());
        assertEquals(newFavorite, retrievedItem);
    }

    @Test
    void updateSuccess() {
        FavoriteItem favoriteToUpdate = favoriteItemDAO.getById(1);
        assertNotNull(favoriteToUpdate);

        favoriteToUpdate.setFavorite(false);
        favoriteItemDAO.update(favoriteToUpdate);

        FavoriteItem retrievedItem = favoriteItemDAO.getById(1);
        assertFalse(retrievedItem.isFavorite());
    }

    @Test
    void deleteFavoriteItem() {
        FavoriteItem favoriteToDelete = favoriteItemDAO.getById(1);
        assertNotNull(favoriteToDelete);

        favoriteItemDAO.delete(favoriteToDelete);
        assertNull(favoriteItemDAO.getById(1));
    }

    @Test
    void deleteFavoriteItemDoesNotCascadeMealOrFood() {
        FavoriteItem favoriteToDelete = favoriteItemDAO.getById(1);
        assertNotNull(favoriteToDelete);

        Meal linkedMeal = favoriteToDelete.getMeal();
        Food linkedFood = favoriteToDelete.getFood();

        favoriteItemDAO.delete(favoriteToDelete);
        assertNull(favoriteItemDAO.getById(1));

        // Ensure the meal and food still exist
        if (linkedMeal != null) {
            assertNotNull(mealDAO.getById(linkedMeal.getId()), "Meal should not be deleted when favorite is removed");
        }
        if (linkedFood != null) {
            assertNotNull(foodDAO.getById(linkedFood.getId()), "Food should not be deleted when favorite is removed");
        }
    }

    @Test
    void getAllSuccess() {
        List<FavoriteItem> items = favoriteItemDAO.getAll();
        assertEquals(4, items.size());
    }

    @Test
    void getByPropertyEqual() {
        List<FavoriteItem> items = favoriteItemDAO.getByPropertyEqual("isFavorite", true);
        assertFalse(items.isEmpty());
        assertEquals(4, items.size());
    }
}