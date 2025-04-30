package com.grittonbelldev.service;

import com.grittonbelldev.dto.nutritionix.*;
import com.grittonbelldev.persistence.NutritionixDao;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Business‐layer facade over NutritionixDao.
 * Keeps resources free of HTTP‐client details.
 */
public class NutritionixService {

    private final NutritionixDao dao;

    /**
     * Constructor – inject Nutritionix appId/appKey from the ServletContext.
     */
    public NutritionixService(String appId, String appKey) {
        this.dao = new NutritionixDao(appId, appKey);
    }

    /** Returns full instant‐search response (both common & branded lists). */
    public NutritionixSearchResponseDto searchAll(String query) {
        return dao.searchInstant(query);
    }

    /** Returns only the “common” items for a type-ahead dropdown. */
    public List<CommonItem> searchCommon(String query) {
        return dao.getCommon(query);
    }

    /** Returns only the “branded” items for a type-ahead dropdown. */
    public List<BrandedItem> searchBranded(String query) {
        return dao.getBranded(query);
    }

    /**
     * Fetch full nutrition details for one branded item.
     * @throws NotFoundException if no item comes back
     */
    public FoodsItem fetchById(String nixItemId) {
        FoodResponse detail = dao.fetchById(nixItemId);
        return detail.getFoods().stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found: " + nixItemId));
    }

    /**
     * Run a natural‐language nutrient analysis.
     * <p>
     * e.g. "1 banana", "3 large eggs", "apple and peanut butter"
     * </p>
     * @param query free‐form food description
     * @return list of FoodsItem with full_nutrients & alt_measures populated
     * @throws NotFoundException if nothing is returned
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
