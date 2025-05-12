package com.grittonbelldev.service;

import com.grittonbelldev.dto.FoodEntryDto;
import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.FoodMealJournal;
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
 * Service layer responsible for managing Meal entities.
 *
 * <p>This service provides methods to create, retrieve, update, and delete meals for a specific user,
 * along with associated food entries. It ensures user-level access scoping and uses DTO mapping
 * to isolate persistence logic from client-facing data.</p>
 */
public class MealService {

    private final Logger logger = LogManager.getLogger(this.getClass());


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
        logger.info("Listing all meals for user {}", userId);
        List<MealResponseDto> results = mealDao.getByPropertyEqual("user.id", userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        logger.debug("Found {} meals for user {}", results.size(), userId);
        return results;
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
        logger.info("Fetching meal {} for user {}", mealId, userId);
        Meal meal = mealDao.getById(mealId);
        if (meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            logger.warn("Meal {} not found or does not belong to user {}", mealId, userId);
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }
        logger.debug("Meal {} found for user {}", mealId, userId);
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
        logger.info("Creating meal for user {}", userId);

        // Retrieve the user who owns this meal
        User user = userDao.getById(userId);
        if (user == null) {
            logger.error("User {} not found during meal creation", userId);
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }

        // Validate required meal fields
        if (dto.getMealName() == null || dto.getMealName().trim().isEmpty()) {
            logger.warn("Missing required field: mealName");
            throw new WebApplicationException("Meal name is required", Response.Status.BAD_REQUEST);
        }
        if (dto.getTimeEaten() == null) {
            logger.warn("Missing required field: timeEaten");
            throw new WebApplicationException("Time eaten is required", Response.Status.BAD_REQUEST);
        }

        // Create and populate the new meal entity
        Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());

        // Save the meal to the database
        mealDao.insert(meal);
        logger.debug("Inserted meal with ID {}", meal.getId());

        // Process and attach each food entry to the meal
        if (dto.getFoods() != null) {
            logger.debug("Adding {} food entries to meal {}", dto.getFoods().size(), meal.getId());

            for (FoodEntryDto foodDto : dto.getFoods()) {
                // Validate required fields from Food and FoodMealJournal
                if (foodDto.getServingSize() == null) {
                    logger.warn("Missing required field: servingSize in food entry");
                    throw new WebApplicationException("Serving size is required for each food", Response.Status.BAD_REQUEST);
                }

                if (foodDto.getFoodName() == null || foodDto.getFoodName().trim().isEmpty()) {
                    logger.warn("Missing required field: foodName in food entry");
                    throw new WebApplicationException("Food name is required for each food", Response.Status.BAD_REQUEST);
                }

                if (foodDto.getCalories() == null || foodDto.getFat() == null
                        || foodDto.getProtein() == null || foodDto.getCarbs() == null) {
                    logger.warn("Missing required nutrient values in food entry: {}", foodDto);
                    throw new WebApplicationException("Calories, fat, protein, and carbs are required for each food", Response.Status.BAD_REQUEST);
                }

                Food food;

                // If a foodId is provided, attempt to look up the existing Food
                if (foodDto.getFoodId() != null) {
                    food = foodDao.getById(foodDto.getFoodId());

                    // If the foodId is invalid, return a 400 error
                    if (food == null) {
                        logger.error("Food ID {} not found during meal creation", foodDto.getFoodId());
                        throw new WebApplicationException(
                                "Food not found: " + foodDto.getFoodId(),
                                Response.Status.BAD_REQUEST
                        );
                    }

                    // TODO: Add logic to check for duplicate foods instead of just adding a new food each time

                } else {
                    // If no foodId is provided, assume this is a new custom food and create it
                    logger.info("No foodId provided. Creating new food from entry.");

                    food = new Food();
                    food.setFoodName(foodDto.getFoodName());
                    food.setCalories(foodDto.getCalories());
                    food.setProtein(foodDto.getProtein());
                    food.setFat(foodDto.getFat());
                    food.setCarbs(foodDto.getCarbs());
                    food.setCholesterol(foodDto.getCholesterol());
                    food.setSodium(foodDto.getSodium());
                    food.setFiber(foodDto.getFiber());
                    food.setSugar(foodDto.getSugar());
                    food.setAddedSugar(foodDto.getAddedSugar());
                    food.setVitaminD(foodDto.getVitaminD());
                    food.setCalcium(foodDto.getCalcium());
                    food.setIron(foodDto.getIron());
                    food.setPotassium(foodDto.getPotassium());
                    food.setNotes(foodDto.getNotes());

                    // Save the newly created Food entry to the database
                    foodDao.insert(food);
                    logger.debug("New food created with ID {}", food.getId());
                }

                // Create a linking FoodMealJournal entry to associate the food with this meal
                FoodMealJournal entry = new FoodMealJournal(meal, food, foodDto.getServingSize());
                fmjDao.insert(entry);
            }
        }

        // Convert and return the Meal as a response DTO
        return toResponseDto(meal);
    }


    /**
     * Updates a meal and its associated food entries if it belongs to the given user.
     *
     * @param userId The ID of the user making the request
     * @param mealId The ID of the meal to update
     * @param dto The updated meal data
     * @return The updated MealResponseDto
     * @throws WebApplicationException If the meal does not exist or does not belong to the user
     */
    public MealResponseDto updateForUser(long userId, long mealId, MealRequestDto dto) {
        logger.info("Updating meal {} for user {}", mealId, userId);

        // Fetch and validate the meal
        Meal mealToUpdate = mealDao.getById(mealId);
        if (mealToUpdate == null || mealToUpdate.getUser() == null || mealToUpdate.getUser().getId() != userId) {
            logger.warn("Meal {} not found or does not belong to user {}", mealId, userId);
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }

        // Validate required top-level fields
        if (dto.getMealName() == null || dto.getMealName().trim().isEmpty()) {
            logger.warn("Missing required field: mealName");
            throw new WebApplicationException("Meal name is required", Response.Status.BAD_REQUEST);
        }
        if (dto.getTimeEaten() == null) {
            logger.warn("Missing required field: timeEaten");
            throw new WebApplicationException("Time eaten is required", Response.Status.BAD_REQUEST);
        }

        // Update top-level fields
        mealToUpdate.setMealName(dto.getMealName());
        mealToUpdate.setTimeEaten(dto.getTimeEaten());
        mealDao.update(mealToUpdate);
        logger.debug("Updated meal {} fields: name='{}', timeEaten='{}'",
                mealId, dto.getMealName(), dto.getTimeEaten());

        // Remove all existing food journal entries linked to this meal
        List<FoodMealJournal> existingFoodLinks = mealToUpdate.getFoodMealEntries();
        for (FoodMealJournal journalEntry : existingFoodLinks) {
            fmjDao.delete(journalEntry);
        }
        logger.debug("Deleted {} existing FoodMealJournal entries for meal {}", existingFoodLinks.size(), mealId);

        // Re-create food entries and links
        if (dto.getFoods() != null && !dto.getFoods().isEmpty()) {
            logger.debug("Processing {} foods from update DTO", dto.getFoods().size());

            for (FoodEntryDto foodDto : dto.getFoods()) {

                // Validate required fields from Food and FoodMealJournal
                if (foodDto.getServingSize() == null) {
                    logger.warn("Missing required field: servingSize in food entry");
                    throw new WebApplicationException("Serving size is required for each food", Response.Status.BAD_REQUEST);
                }

                if (foodDto.getFoodName() == null || foodDto.getFoodName().trim().isEmpty()) {
                    logger.warn("Missing required field: foodName in food entry");
                    throw new WebApplicationException("Food name is required for each food", Response.Status.BAD_REQUEST);
                }

                if (foodDto.getCalories() == null || foodDto.getFat() == null
                        || foodDto.getProtein() == null || foodDto.getCarbs() == null) {
                    logger.warn("Missing required nutrient values in food entry: {}", foodDto);
                    throw new WebApplicationException("Calories, fat, protein, and carbs are required for each food", Response.Status.BAD_REQUEST);
                }

                Food linkedFood;

                // Case 1: Existing food entry to be updated
                if (foodDto.getFoodId() != null) {
                    linkedFood = foodDao.getById(foodDto.getFoodId());

                    if (linkedFood == null) {
                        logger.error("Food ID {} not found in update", foodDto.getFoodId());
                        throw new WebApplicationException("Food not found: " + foodDto.getFoodId(), Response.Status.BAD_REQUEST);
                    }

                    // Update fields in the existing food record
                    linkedFood.setFoodName(foodDto.getFoodName());
                    linkedFood.setCalories(foodDto.getCalories());
                    linkedFood.setProtein(foodDto.getProtein());
                    linkedFood.setFat(foodDto.getFat());
                    linkedFood.setCarbs(foodDto.getCarbs());
                    linkedFood.setCholesterol(foodDto.getCholesterol());
                    linkedFood.setSodium(foodDto.getSodium());
                    linkedFood.setFiber(foodDto.getFiber());
                    linkedFood.setSugar(foodDto.getSugar());
                    linkedFood.setAddedSugar(foodDto.getAddedSugar());
                    linkedFood.setVitaminD(foodDto.getVitaminD());
                    linkedFood.setCalcium(foodDto.getCalcium());
                    linkedFood.setIron(foodDto.getIron());
                    linkedFood.setPotassium(foodDto.getPotassium());
                    linkedFood.setNotes(foodDto.getNotes());

                    foodDao.update(linkedFood);
                    logger.debug("Updated food ID {} with new nutrition data", linkedFood.getId());

                } else {
                    // Case 2: New food entry to be created
                    linkedFood = new Food();
                    linkedFood.setFoodName(foodDto.getFoodName());
                    linkedFood.setCalories(foodDto.getCalories());
                    linkedFood.setProtein(foodDto.getProtein());
                    linkedFood.setFat(foodDto.getFat());
                    linkedFood.setCarbs(foodDto.getCarbs());
                    linkedFood.setCholesterol(foodDto.getCholesterol());
                    linkedFood.setSodium(foodDto.getSodium());
                    linkedFood.setFiber(foodDto.getFiber());
                    linkedFood.setSugar(foodDto.getSugar());
                    linkedFood.setAddedSugar(foodDto.getAddedSugar());
                    linkedFood.setVitaminD(foodDto.getVitaminD());
                    linkedFood.setCalcium(foodDto.getCalcium());
                    linkedFood.setIron(foodDto.getIron());
                    linkedFood.setPotassium(foodDto.getPotassium());
                    linkedFood.setNotes(foodDto.getNotes());

                    foodDao.insert(linkedFood);
                    logger.debug("Created new food entry with ID {}", linkedFood.getId());
                }

                // Link food to the updated meal
                FoodMealJournal newLink = new FoodMealJournal(mealToUpdate, linkedFood, foodDto.getServingSize());
                fmjDao.insert(newLink);
                logger.debug("Linked food ID {} to meal ID {} with serving size {}",
                        linkedFood.getId(), mealToUpdate.getId(), foodDto.getServingSize());
            }
        }

        logger.info("Meal {} successfully updated for user {}", mealId, userId);
        return toResponseDto(mealToUpdate);
    }

    /**
     * Deletes a meal if it belongs to the user.
     *
     * @param userId the user's ID
     * @param mealId the meal's ID
     * @throws WebApplicationException if the meal is not found or not owned by the user
     */
    public void deleteForUser(long userId, long mealId) {
        logger.info("Deleting meal {} for user {}", mealId, userId);
        Meal meal = mealDao.getById(mealId);
        if (meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            logger.warn("Meal {} not found or does not belong to user {}", mealId, userId);
            throw new WebApplicationException("Meal not found", Response.Status.NOT_FOUND);
        }

        mealDao.delete(meal);
        logger.debug("Meal {} deleted", mealId);
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

        // Map each food entry with full nutritional info
        List<FoodEntryDto> foods = meal.getFoodMealEntries().stream()
                .map(e -> {
                    Food f = e.getFood();
                    FoodEntryDto fe = new FoodEntryDto();
                    fe.setFoodId(f.getId());
                    fe.setFoodName(f.getFoodName());
                    fe.setServingSize(e.getServingSize());
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
                    return fe;
                })
                .collect(Collectors.toList());

        r.setFoods(foods);
        return r;
    }
}
