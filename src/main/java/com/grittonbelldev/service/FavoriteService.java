package com.grittonbelldev.service;

import com.grittonbelldev.dto.FoodEntryDto;
import com.grittonbelldev.dto.FoodResponseDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.entity.FavoriteItem;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing FavoriteItem records.
 *
 * This class encapsulates the logic to mark meals or foods as favorites,
 * remove them from favorites, and retrieve lists of favorited items
 * for a given user. All operations are scoped to an individual user.
 */
public class FavoriteService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final GenericDAO<FavoriteItem> favDao  = new GenericDAO<>(FavoriteItem.class);
    private final GenericDAO<Meal>         mealDao = new GenericDAO<>(Meal.class);
    private final GenericDAO<Food>         foodDao = new GenericDAO<>(Food.class);
    private final GenericDAO<User>         userDao = new GenericDAO<>(User.class);

    /**
     * Marks a meal as favorite for the specified user.
     *
     * @param userId the user ID
     * @param mealId the meal ID
     */
    public void favoriteMealForUser(long userId, long mealId) {
        logger.info("Favoriting meal ID {} for user ID {}", mealId, userId);
        User user = userDao.getById(userId);
        Meal meal = mealDao.getById(mealId);
        if (user == null || meal == null) {
            logger.warn("User or Meal not found for favoriting. userId={}, mealId={}", userId, mealId);
            throw new WebApplicationException("User or Meal not found", Response.Status.NOT_FOUND);
        }
        FavoriteItem fav = new FavoriteItem();
        fav.setUser(user);
        fav.setMeal(meal);
        fav.setFavorite(true);
        favDao.insert(fav);
        logger.debug("Meal favorited successfully");
    }

    /**
     * Removes a meal from the user's favorites.
     *
     * @param userId the user ID
     * @param mealId the meal ID
     */
    public void unfavoriteMealForUser(long userId, long mealId) {
        logger.info("Unfavoriting meal ID {} for user ID {}", mealId, userId);
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId).stream()
                .filter(f -> f.getMeal() != null && f.getMeal().getId() == mealId)
                .collect(Collectors.toList());
        found.forEach(favDao::delete);
        logger.debug("Unfavorited {} meal entries", found.size());
    }

    /**
     * Marks a food item as favorite for the specified user.
     *
     * @param userId the user ID
     * @param foodId the food ID
     */
    public void favoriteFoodForUser(long userId, long foodId) {
        logger.info("Favoriting food ID {} for user ID {}", foodId, userId);
        User user = userDao.getById(userId);
        Food food = foodDao.getById(foodId);
        if (user == null || food == null) {
            logger.warn("User or Food not found for favoriting. userId={}, foodId={}", userId, foodId);
            throw new WebApplicationException("User or Food not found", Response.Status.NOT_FOUND);
        }
        FavoriteItem fav = new FavoriteItem();
        fav.setUser(user);
        fav.setFood(food);
        fav.setFavorite(true);
        favDao.insert(fav);
        logger.debug("Food favorited successfully");
    }

    /**
     * Removes a food item from the user's favorites.
     *
     * @param userId the user ID
     * @param foodId the food ID
     */
    public void unfavoriteFoodForUser(long userId, long foodId) {
        logger.info("Unfavoriting food ID {} for user ID {}", foodId, userId);
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId).stream()
                .filter(f -> f.getFood() != null && f.getFood().getId() == foodId)
                .collect(Collectors.toList());
        found.forEach(favDao::delete);
        logger.debug("Unfavorited {} food entries", found.size());
    }

    /**
     * Retrieves a list of the user's favorited meals, including detailed nutritional info.
     *
     * @param userId the user ID
     * @return list of MealResponseDto with embedded FoodEntryDto objects
     */
    public List<MealResponseDto> listFavoriteMeals(Long userId) {
        logger.debug("Fetching favorite meals for userId: {}", userId);
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId);
        return found.stream()
                .filter(fav -> fav.getMeal() != null)
                .map(fav -> {
                    Meal m = fav.getMeal();
                    MealResponseDto dto = new MealResponseDto();
                    dto.setId(m.getId());
                    dto.setMealName(m.getMealName());
                    dto.setTimeEaten(m.getTimeEaten());

                    // Map associated food entries to DTOs
                    List<FoodEntryDto> foods = m.getFoodMealEntries().stream()
                            .map(entry -> {
                                Food f = entry.getFood();
                                FoodEntryDto fe = new FoodEntryDto();
                                fe.setFoodId(f.getId());
                                fe.setFoodName(f.getFoodName());
                                fe.setCalories(f.getCalories());
                                fe.setProtein(f.getProtein());
                                fe.setCarbs(f.getCarbs());
                                fe.setFat(f.getFat());
                                fe.setCholesterol(f.getCholesterol());
                                fe.setSodium(f.getSodium());
                                fe.setFiber(f.getFiber());
                                fe.setSugar(f.getSugar());
                                fe.setAddedSugar(f.getAddedSugar());
                                fe.setVitaminD(f.getVitaminD());
                                fe.setCalcium(f.getCalcium());
                                fe.setIron(f.getIron());
                                fe.setPotassium(f.getPotassium());
                                fe.setNotes(f.getNotes());
                                fe.setServingSize(entry.getServingSize());
                                return fe;
                            })
                            .collect(Collectors.toList());

                    dto.setFoods(foods);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of the user's favorited individual food items.
     *
     * @param userId the user ID
     * @return list of FoodResponseDto representing favorited foods
     */
    public List<FoodResponseDto> listFavoriteFoods(Long userId) {
        logger.debug("Fetching favorite foods for userId: {}", userId);
        List<FavoriteItem> found = favDao.getByPropertyEqual("user.id", userId);
        return found.stream()
                .filter(fav -> fav.getFood() != null)
                .map(fav -> {
                    Food f = fav.getFood();
                    FoodResponseDto dto = new FoodResponseDto();
                    dto.setId(f.getId());
                    dto.setFoodName(f.getFoodName());
                    dto.setCalories(f.getCalories());
                    dto.setProtein(f.getProtein());
                    dto.setCarbs(f.getCarbs());
                    dto.setFat(f.getFat());
                    dto.setCholesterol(f.getCholesterol());
                    dto.setSodium(f.getSodium());
                    dto.setFiber(f.getFiber());
                    dto.setSugar(f.getSugar());
                    dto.setAddedSugar(f.getAddedSugar());
                    dto.setVitaminD(f.getVitaminD());
                    dto.setCalcium(f.getCalcium());
                    dto.setIron(f.getIron());
                    dto.setPotassium(f.getPotassium());
                    dto.setNotes(f.getNotes());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
