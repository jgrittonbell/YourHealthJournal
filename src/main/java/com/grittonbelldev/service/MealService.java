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
 * Service layer responsible for managing Meal entities.
 *
 * <p>This service provides methods to create, retrieve, update, and delete meals for a specific user,
 * along with associated food entries. It ensures user-level access scoping and uses DTO mapping
 * to isolate persistence logic from client-facing data.</p>
 */
public class MealService {

    // DAOs for working with Meal, FoodMealJournal, Food, and User entities
    private final GenericDAO<Meal> mealDao = new GenericDAO<>(Meal.class);
    private final GenericDAO<FoodMealJournal> fmjDao = new GenericDAO<>(FoodMealJournal.class);
    private final GenericDAO<Food> foodDao = new GenericDAO<>(Food.class);
    private final GenericDAO<User> userDao = new GenericDAO<>(User.class);

    /**
     * Returns a list of all meals owned by the given user.
     *
     * @param userId the ID of the user whose meals should be listed
     * @return a list of MealResponseDto representing each meal
     */
    public List<MealResponseDto> listAllForUser(long userId) {
        return mealDao.getByPropertyEqual("user.id", userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific meal by ID, verifying ownership by the user.
     *
     * @param userId the user's ID
     * @param mealId the meal's ID
     * @return the corresponding MealResponseDto
     * @throws WebApplicationException if the meal does not exist or does not belong to the user
     */
    public MealResponseDto findForUser(long userId, long mealId) {
        Meal meal = mealDao.getById(mealId);
        if (meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }
        return toResponseDto(meal);
    }

    /**
     * Creates a new meal associated with the given user and adds any listed food items.
     *
     * @param userId the ID of the user who owns the new meal
     * @param dto the DTO containing meal name, time, and foods
     * @return a MealResponseDto representing the newly created meal
     * @throws WebApplicationException if the user or any food entries are invalid
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
     * Updates a meal's name and time if it belongs to the user.
     *
     * @param userId the user's ID
     * @param mealId the meal's ID
     * @param dto the updated meal data
     * @return the updated MealResponseDto
     * @throws WebApplicationException if the meal does not exist or does not belong to the user
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
     * Deletes a meal if it belongs to the user.
     *
     * @param userId the user's ID
     * @param mealId the meal's ID
     * @throws WebApplicationException if the meal is not found or not owned by the user
     */
    public void deleteForUser(long userId, long mealId) {
        Meal meal = mealDao.getById(mealId);
        if (meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }
        mealDao.delete(meal);
    }


    /**
     * Maps a Meal entity to a MealResponseDto, including associated food entries.
     *
     * @param meal the Meal entity
     * @return the corresponding MealResponseDto
     */
    private MealResponseDto toResponseDto(Meal meal) {
        MealResponseDto r = new MealResponseDto();
        r.setId(meal.getId());
        r.setMealName(meal.getMealName());
        r.setTimeEaten(meal.getTimeEaten());

        // Map each food entry to its corresponding DTO representation
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
