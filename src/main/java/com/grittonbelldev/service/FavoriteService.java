
package com.grittonbelldev.service;

import com.grittonbelldev.entity.FavoriteItem;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.persistence.GenericDAO;

import java.util.List;

/**
 * Service layer for managing FavoriteItem entities.
 * <p>
 * Handles favoriting and unfavoriting of meals and foods,
 * mapping operations to persistence via GenericDAO.
 * </p>
 */
public class FavoriteService {
    /** DAO for FavoriteItem join entity persistence. */
    private final GenericDAO<FavoriteItem> favDao = new GenericDAO<>(FavoriteItem.class);
    /** DAO for Meal entity persistence. */
    private final GenericDAO<Meal> mealDao = new GenericDAO<>(Meal.class);
    /** DAO for Food entity persistence. */
    private final GenericDAO<Food> foodDao = new GenericDAO<>(Food.class);

    /**
     * Marks a meal as a favorite for the current user context.
     *
     * @param mealId identifier of the Meal to favorite
     * @throws IllegalArgumentException if the Meal does not exist
     */
    public void favoriteMeal(Long mealId) {
        // Lookup the Meal; throw if not found
        Meal meal = mealDao.getById(mealId);
        if (meal == null) {
            throw new IllegalArgumentException("Cannot favorite; meal not found for id " + mealId);
        }
        // Create and persist a new FavoriteItem pointing to the meal
        FavoriteItem fav = new FavoriteItem();
        fav.setMeal(meal);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    /**
     * Removes the favorite flag from a meal by deleting FavoriteItem entries.
     *
     * @param mealId identifier of the Meal to unfavorite
     */
    public void unfavoriteMeal(Long mealId) {
        // Find all FavoriteItems for this meal and delete them
        List<FavoriteItem> found = favDao.getByPropertyEqual("meal.id", mealId);
        for (FavoriteItem f : found) {
            favDao.delete(f);
        }
    }

    /**
     * Marks a food item as a favorite for the current user context.
     *
     * @param foodId identifier of the Food to favorite
     * @throws IllegalArgumentException if the Food does not exist
     */
    public void favoriteFood(Long foodId) {
        // Lookup the Food; throw if not found
        Food food = foodDao.getById(foodId);
        if (food == null) {
            throw new IllegalArgumentException("Cannot favorite; food not found for id " + foodId);
        }
        // Create and persist a new FavoriteItem pointing to the food
        FavoriteItem fav = new FavoriteItem();
        fav.setFood(food);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    /**
     * Removes the favorite flag from a food by deleting FavoriteItem entries.
     *
     * @param foodId identifier of the Food to unfavorite
     */
    public void unfavoriteFood(Long foodId) {
        // Find all FavoriteItems for this food and delete them
        List<FavoriteItem> found = favDao.getByPropertyEqual("food.id", foodId);
        for (FavoriteItem f : found) {
            favDao.delete(f);
        }
    }
}
