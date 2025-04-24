package com.grittonbelldev.service;

import com.grittonbelldev.entity.FavoriteItem;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FavoriteService {
    @Inject
    private GenericDAO<FavoriteItem> favDao;

    @Inject
    private GenericDAO<Meal> mealDao;

    @Inject
    private GenericDAO<Food> foodDao;

    public void favoriteMeal(Long mealId) {
        Meal meal = mealDao.getById(mealId);
        FavoriteItem fav = new FavoriteItem();
        fav.setMeal(meal);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    public void unfavoriteMeal(Long mealId) {
        // find and delete existing favorite
        favDao.getByPropertyEqual("meal.id", mealId)
                .forEach(favDao::delete);
    }

    public void favoriteFood(Long foodId) {
        Food food = foodDao.getById(foodId);
        FavoriteItem fav = new FavoriteItem();
        fav.setFood(food);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    public void unfavoriteFood(Long foodId) {
        favDao.getByPropertyEqual("food.id", foodId)
                .forEach(favDao::delete);
    }
}