package com.grittonbelldev.service;

import com.grittonbelldev.persistence.NutritionixDao;
import com.grittonbelldev.dto.nutritionix.CommonItem;
import com.grittonbelldev.dto.nutritionix.BrandedItem;
import com.grittonbelldev.dto.nutritionix.NutritionixSearchResponseDto;
import com.grittonbelldev.dto.nutritionix.NutritionixFoodDto;

import java.util.List;

/**
 * Business‐layer facade over NutritionixDao.
 * Keeps resources free of HTTP‐client details.
 */
public class NutritionixService {

    private final NutritionixDao dao;

    /** A constructor to inject appId/appKey from ServletContext*/
    public NutritionixService(String appId, String appKey) {
        this.dao = new NutritionixDao(appId, appKey);
    }

    /** Returns full search response (both common & branded lists). */
    public NutritionixSearchResponseDto searchAll(String query) {
        return dao.searchInstant(query);
    }

    /** Returns only the “common” items for a possible type-ahead dropdown. */
    public List<CommonItem> searchCommon(String query) {
        return dao.getCommon(query);
    }

    /** Returns only the “branded” items for a possible type-ahead dropdown. */
    public List<BrandedItem> searchBranded(String query) {
        return dao.getBranded(query);
    }

}