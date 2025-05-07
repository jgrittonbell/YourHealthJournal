package com.grittonbelldev.service;

import com.grittonbelldev.dto.nutritionix.*;
import com.grittonbelldev.persistence.NutritionixDao;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Service layer for handling business logic related to Nutritionix data.
 *
 * This class provides a higher-level abstraction over {@link NutritionixDao},
 * shielding the API layer from direct interaction with HTTP clients or external APIs.
 * All Nutritionix-related operations (instant search, lookup by ID, natural-language
 * nutrient analysis) are encapsulated here.
 */
public class NutritionixService {

    private final NutritionixDao dao;

    /**
     * Constructs the service by instantiating a DAO with the provided Nutritionix credentials.
     * The appId and appKey are typically loaded from ServletContext at runtime.
     *
     * @param appId  Nutritionix Application ID
     * @param appKey Nutritionix Application Key
     */
    public NutritionixService(String appId, String appKey) {
        this.dao = new NutritionixDao(appId, appKey);
    }

    /**
     * Performs an instant search query that returns both common and branded food items.
     *
     * @param query the search term entered by the user
     * @return DTO containing both common and branded search results
     */
    public NutritionixSearchResponseDto searchAll(String query) {
        return dao.searchInstant(query);
    }

    /**
     * Searches only the “common” food results (e.g., "apple", "egg").
     * Used primarily for type-ahead dropdowns and natural items.
     *
     * @param query the search term entered by the user
     * @return list of common food entries
     */
    public List<CommonItem> searchCommon(String query) {
        return dao.getCommon(query);
    }

    /**
     * Searches only the “branded” food results (e.g., packaged or commercial items).
     * Used for showing branded options in dropdowns.
     *
     * @param query the search term entered by the user
     * @return list of branded food entries
     */
    public List<BrandedItem> searchBranded(String query) {
        return dao.getBranded(query);
    }

    /**
     * Fetches detailed nutritional information for a specific branded item.
     *
     * @param nixItemId the Nutritionix item ID to look up
     * @return the first {@link FoodsItem} from the Nutritionix response
     * @throws NotFoundException if no item is found
     */
    public FoodsItem fetchById(String nixItemId) {
        FoodResponse detail = dao.fetchById(nixItemId);
        return detail.getFoods().stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found: " + nixItemId));
    }

    /**
     * Analyzes a free-form string describing food and returns nutrient data.
     * Example queries include: "1 banana", "3 large eggs", "apple and peanut butter".
     *
     * @param query user-entered text describing food consumption
     * @return list of {@link FoodsItem} objects containing full nutrients and alt measures
     * @throws NotFoundException if Nutritionix returns an empty list
     */
    public List<FoodsItem> naturalNutrients(String query) {
        FoodResponse resp = dao.naturalNutrients(query);
        List<FoodsItem> foods = resp.getFoods();
        if (foods.isEmpty()) {
            throw new NotFoundException("No nutrient data found for: " + query);
        }
        return foods;
    }
}
