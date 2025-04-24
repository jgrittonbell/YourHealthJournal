
package com.grittonbelldev.service;

import com.grittonbelldev.dto.FoodEntryDto;
import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.FoodMealJournal;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.persistence.GenericDAO;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Service layer for managing Meal entities.
 * <p>
 * Responsible for CRUD operations on meals, mapping between DTOs and entities,
 * and orchestrating persistence actions via GenericDAO.
 * </p>
 */
public class MealService {
    /** DAO for Meal entity persistence. */
    private final GenericDAO<Meal> mealDao = new GenericDAO<>(Meal.class);
    /** DAO for FoodMealJournal join entity persistence. */
    private final GenericDAO<FoodMealJournal> fmjDao = new GenericDAO<>(FoodMealJournal.class);
    /** DAO for Food entity persistence. */
    private final GenericDAO<Food> foodDao = new GenericDAO<>(Food.class);

    /**
     * Retrieves all meals and converts them to response DTOs.
     *
     * @return list of MealResponseDto representing all persisted meals
     */
    public List<MealResponseDto> listAll() {
        // Fetch all Meal entities and map each to a DTO
        return mealDao.getAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single meal by its identifier.
     *
     * @param id the primary key of the Meal to find
     * @return MealResponseDto of the found meal
     * @throws IllegalArgumentException if no Meal is found for the given id
     */
    public MealResponseDto find(Long id) {
        Meal meal = mealDao.getById(id);
        if (meal == null) {
            throw new IllegalArgumentException("Meal not found for id " + id);
        }
        return toResponseDto(meal);
    }

    /**
     * Creates a new Meal along with any provided food entries.
     *
     * @param dto data transfer object containing meal data and optional foods
     * @return MealResponseDto representing the newly created meal
     */
    public MealResponseDto create(MealRequestDto dto) {
        // Map request DTO to entity
        Meal meal = new Meal();
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());
        // Persist the Meal to generate an identifier
        mealDao.insert(meal);

        // If food entries are provided, create join entities
        if (dto.getFoods() != null) {
            for (FoodEntryDto fe : dto.getFoods()) {
                // Lookup the Food entity by ID
                Food food = foodDao.getById(fe.getFoodId());
                // Create and persist the join entity linking meal to food
                FoodMealJournal entry = new FoodMealJournal(meal, food, fe.getServingSize());
                fmjDao.insert(entry);
            }
        }

        // Convert the fully populated Meal entity to response DTO
        return toResponseDto(meal);
    }

    /**
     * Updates an existing Meal's basic fields (name and time).
     *
     * @param id  identifier of the Meal to update
     * @param dto data transfer object containing updated values
     * @return MealResponseDto representing the updated meal
     * @throws IllegalArgumentException if no Meal is found for the given id
     */
    public MealResponseDto update(Long id, MealRequestDto dto) {
        Meal meal = mealDao.getById(id);
        if (meal == null) {
            throw new IllegalArgumentException("Cannot update; Meal not found for id " + id);
        }
        // Apply updates from DTO
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());
        mealDao.update(meal);
        return toResponseDto(meal);
    }

    /**
     * Deletes a Meal and its associated food entries.
     *
     * @param id identifier of the Meal to delete
     * @throws IllegalArgumentException if no Meal is found for the given id
     */
    public void delete(Long id) {
        Meal meal = mealDao.getById(id);
        if (meal == null) {
            throw new IllegalArgumentException("Cannot delete; Meal not found for id " + id);
        }
        mealDao.delete(meal);
    }

    /**
     * Maps a Meal entity to its response DTO representation.
     * Populates basic fields and associated food entry DTOs.
     *
     * @param meal the Meal entity to convert
     * @return MealResponseDto populated with entity data
     */
    private MealResponseDto toResponseDto(Meal meal) {
        MealResponseDto r = new MealResponseDto();
        r.setId(meal.getId());
        r.setMealName(meal.getMealName());
        r.setTimeEaten(meal.getTimeEaten());
        // Map each join entry to a simple DTO
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
