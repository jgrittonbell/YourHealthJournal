package com.grittonbelldev.service;

import com.grittonbelldev.dto.FoodResponseDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.entity.FavoriteItem;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing FavoriteItem entities, scoped per user.
 */
public class FavoriteService {
    private final GenericDAO<FavoriteItem> favDao  = new GenericDAO<>(FavoriteItem.class);
    private final GenericDAO<Meal>         mealDao = new GenericDAO<>(Meal.class);
    private final GenericDAO<Food>         foodDao = new GenericDAO<>(Food.class);
    private final GenericDAO<User>         userDao = new GenericDAO<>(User.class);

    /**
     * Marks a meal as a favorite for the given user.
     *
     * @param userId ID of the user
     * @param mealId ID of the meal to favorite
     */
    public void favoriteMealForUser(long userId, long mealId) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }
        Meal meal = mealDao.getById(mealId);
        if (meal == null) {
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }
        FavoriteItem fav = new FavoriteItem();
        fav.setUser(user);
        fav.setMeal(meal);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    /**
     * Removes the favorite marking of a meal for the given user.
     *
     * @param userId ID of the user
     * @param mealId ID of the meal to unfavorite
     */
    public void unfavoriteMealForUser(long userId, long mealId) {
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId).stream()
                .filter(f -> f.getMeal() != null && f.getMeal().getId() == mealId)
                .collect(Collectors.toList());
        for (FavoriteItem f : found) {
            favDao.delete(f);
        }
    }

    /**
     * Marks a food item as a favorite for the given user.
     *
     * @param userId ID of the user
     * @param foodId ID of the food to favorite
     */
    public void favoriteFoodForUser(long userId, long foodId) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }
        Food food = foodDao.getById(foodId);
        if (food == null) {
            throw new WebApplicationException("Food not found", Response.Status.NOT_FOUND);
        }
        FavoriteItem fav = new FavoriteItem();
        fav.setUser(user);
        fav.setFood(food);
        fav.setFavorite(true);
        favDao.insert(fav);
    }

    /**
     * Removes the favorite marking of a food for the given user.
     *
     * @param userId ID of the user
     * @param foodId ID of the food to unfavorite
     */
    public void unfavoriteFoodForUser(long userId, long foodId) {
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId).stream()
                .filter(f -> f.getFood() != null && f.getFood().getId() == foodId)
                .collect(Collectors.toList());
        for (FavoriteItem f : found) {
            favDao.delete(f);
        }
    }

    /**
     * List all MealResponseDto’s favorited by the given user.
     */
    public List<MealResponseDto> listFavoriteMeals(Long userId) {
        // find all FavoriteItems for this user & meal != null
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId);
        return found.stream()
                .map(fav -> {
                    Meal m = fav.getMeal();
                    MealResponseDto dto = new MealResponseDto();
                    dto.setId(m.getId());
                    dto.setMealName(m.getMealName());
                    dto.setTimeEaten(m.getTimeEaten());
                    // …and map foods if you like…
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * List all FoodsItem’s favorited by the given user.
     */
    public List<FoodResponseDto> listFavoriteFoods(Long userId) {
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId);
        return found.stream()
                .map(fav -> {
                    Food f = fav.getFood();
                    FoodResponseDto dto = new FoodResponseDto();
                    dto.setId(f.getId());
                    dto.setFoodName(f.getFoodName());
                    // …other fields…
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
