package com.grittonbelldev.service;

import com.grittonbelldev.dto.FoodEntryDto;
import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.entity.Food;
import com.grittonbelldev.entity.FoodMealJournal;
import com.grittonbelldev.entity.Meal;
import com.grittonbelldev.persistence.GenericDAO;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MealService {
    @Inject
    private GenericDAO<Meal> mealDao;

    @Inject
    private GenericDAO<FoodMealJournal> fmjDao;

    @Inject
    private GenericDAO<Food> foodDao;

    public List<MealResponseDto> listAll() {
        return mealDao.getAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public MealResponseDto find(Long id) {
        Meal meal = mealDao.getById(id);
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
        meal.setMealName(dto.getMealName());
        meal.setTimeEaten(dto.getTimeEaten());
        mealDao.update(meal);
        return toResponseDto(meal);
    }

    public void delete(Long id) {
        Meal meal = mealDao.getById(id);
        mealDao.delete(meal);
    }

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