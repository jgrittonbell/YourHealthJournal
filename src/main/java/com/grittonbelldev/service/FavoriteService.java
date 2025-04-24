
package com.grittonbelldev.service;

import com.grittonbelldev.entity.FavoriteItem;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.persistence.GenericDAO;

import java.util.List;

public class FavoriteService {
    private final GenericDAO<FavoriteItem> favDao = new GenericDAO<>(FavoriteItem.class);
    private final GenericDAO<Meal> mealDao = new GenericDAO<>(Meal.class);
    private final GenericDAO<Food> foodDao = new GenericDAO<>(Food.class);

    public void favoriteMeal(Long mealId) {
        Meal meal = mealDao.getById(mealId);
        FavoriteItem fav = new FavoriteItem();
        fav.setMeal(meal);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    public void unfavoriteMeal(Long mealId) {
        List<FavoriteItem> found = favDao.getByPropertyEqual("meal.id", mealId);
        for (FavoriteItem f : found) {
            favDao.delete(f);
        }
    }

    public void favoriteFood(Long foodId) {
        Food food = foodDao.getById(foodId);
        FavoriteItem fav = new FavoriteItem();
        fav.setFood(food);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    public void unfavoriteFood(Long foodId) {
        List<FavoriteItem> found = favDao.getByPropertyEqual("food.id", foodId);
        for (FavoriteItem f : found) {
            favDao.delete(f);
        }
    }
}
