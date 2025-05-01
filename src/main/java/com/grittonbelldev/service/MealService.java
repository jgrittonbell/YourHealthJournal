package com.grittonbelldev.service;

import com.grittonbelldev.dto.FoodEntryDto;
import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.FoodMealJournal;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing Meal entities, scoped per user.
 */
public class MealService {
    private final GenericDAO<Meal> mealDao = new GenericDAO<>(Meal.class);
    private final GenericDAO<FoodMealJournal> fmjDao = new GenericDAO<>(FoodMealJournal.class);
    private final GenericDAO<Food> foodDao = new GenericDAO<>(Food.class);
    private final GenericDAO<User> userDao = new GenericDAO<>(User.class);

    /**
     * List all meals belonging to the given user.
     */
    public List<MealResponseDto> listAllForUser(long userId) {
        return mealDao.getByPropertyEqual("user.id", userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Find one meal by ID, but only if it belongs to the given user.
     */
    public MealResponseDto findForUser(long userId, long mealId) {
        Meal meal = mealDao.getById(mealId);
        if (meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }
        return toResponseDto(meal);
    }

    /**
     * Create a new meal owned by the given user, with optional foods.
     */
    public MealResponseDto createForUser(long userId, MealRequestDto dto) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }
        Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());
        mealDao.insert(meal);

        if (dto.getFoods() != null) {
            for (FoodEntryDto fe : dto.getFoods()) {
                Food food = foodDao.getById(fe.getFoodId());
                if (food == null) {
                    throw new WebApplicationException(
                            "Food not found: " + fe.getFoodId(),
                            Response.Status.BAD_REQUEST
                    );
                }
                FoodMealJournal entry = new FoodMealJournal(meal, food, fe.getServingSize());
                fmjDao.insert(entry);
            }
        }
        return toResponseDto(meal);
    }

    /**
     * Update an existing meal’s name and time, only if it belongs to the given user.
     */
    public MealResponseDto updateForUser(long userId, long mealId, MealRequestDto dto) {
        Meal meal = mealDao.getById(mealId);
        if (meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());
        mealDao.update(meal);
        return toResponseDto(meal);
    }

    /**
     * Delete a meal, only if it belongs to the given user.
     */
    public void deleteForUser(long userId, long mealId) {
        Meal meal = mealDao.getById(mealId);
        if (meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }
        mealDao.delete(meal);
    }

    // ------------------------------------------------------------------------
    // Legacy methods (unscoped) left here if needed—but prefer the per-user variants
    // ------------------------------------------------------------------------

    public List<MealResponseDto> listAll() {
        return mealDao.getAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public MealResponseDto find(Long id) {
        Meal meal = mealDao.getById(id);
        if (meal == null) {
            throw new WebApplicationException(
                    "Meal not found for id " + id,
                    Response.Status.NOT_FOUND
            );
        }
        return toResponseDto(meal);
    }

    public MealResponseDto create(MealRequestDto dto) {
        Meal meal = new Meal();
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());
        mealDao.insert(meal);
        if (dto.getFoods() != null) {
            for (FoodEntryDto fe : dto.getFoods()) {
                Food food = foodDao.getById(fe.getFoodId());
                FoodMealJournal entry = new FoodMealJournal(meal, food, fe.getServingSize());
                fmjDao.insert(entry);
            }
        }
        return toResponseDto(meal);
    }

    public MealResponseDto update(Long id, MealRequestDto dto) {
        Meal meal = mealDao.getById(id);
        if (meal == null) {
            throw new WebApplicationException(
                    "Meal not found for id " + id,
                    Response.Status.NOT_FOUND
            );
        }
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());
        mealDao.update(meal);
        return toResponseDto(meal);
    }

    public void delete(Long id) {
        Meal meal = mealDao.getById(id);
        if (meal == null) {
            throw new WebApplicationException(
                    "Meal not found for id " + id,
                    Response.Status.NOT_FOUND
            );
        }
        mealDao.delete(meal);
    }

    /**
     * Maps a Meal entity (and its FoodMealJournal entries) to a DTO.
     */
    private MealResponseDto toResponseDto(Meal meal) {
        MealResponseDto r = new MealResponseDto();
        r.setId(meal.getId());
        r.setMealName(meal.getMealName());
        r.setTimeEaten(meal.getTimeEaten());
        List<FoodEntryDto> foods = meal.getFoodMealEntries().stream()
                .map(e -> {
                    FoodEntryDto fe = new FoodEntryDto();
                    fe.setFoodId(e.getFood().getId());
                    fe.setServingSize(e.getServingSize());
                    return fe;
                })
                .collect(Collectors.toList());
        r.setFoods(foods);
        return r;
    }
}