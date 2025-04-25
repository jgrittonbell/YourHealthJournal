package com.grittonbelldev.persistence;

import com.grittonbelldev.dto.nutritionix.CommonItem;
import com.grittonbelldev.dto.nutritionix.BrandedItem;
import com.grittonbelldev.dto.nutritionix.NutritionixSearchResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Simple integration test for NutritionixDao.
 * Requires env vars nutritionixID and nutritionixKey.
 */
public class NutritionixDaoTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void testSearchInstant() {
        String appId  = System.getenv("nutritionixID");
        String appKey = System.getenv("nutritionixKey");
        assertNotNull("Set nutritionixID", appId);
        assertNotNull("Set nutritionixKey", appKey);

        NutritionixDao dao = new NutritionixDao(appId, appKey);
        NutritionixSearchResponseDto resp = dao.searchInstant("banana");
        assertNotNull(resp);
        assertNotNull(resp.getCommon());
        assertFalse("Expect common list", resp.getCommon().isEmpty());

        // Log details of retrieved common items
        for (CommonItem item : resp.getCommon()) {
            logger.info("CommonItem - tagId: {} | foodName: {}",
                    item.getTagId(), item.getFoodName());
        }

        // Log details of retrieved branded items (if any)
        if (resp.getBranded() != null) {
            for (BrandedItem brand : resp.getBranded()) {
                logger.info("BrandedItem - nixItemId: {} | foodName: {}",
                        brand.getNixItemId(), brand.getFoodName());
            }
        }

        // Optionally check a field
        assertNotNull(resp.getCommon().get(0).getFoodName());
    }
}