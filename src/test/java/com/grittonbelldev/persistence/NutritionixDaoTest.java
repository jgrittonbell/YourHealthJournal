package com.grittonbelldev.persistence;

import com.grittonbelldev.dto.nutritionix.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Integration tests for NutritionixDao against the real Nutritionix Track API.
 *
 * Requires environment variables:
 *   • nutritionixID
 *   • nutritionixKey
 */
public class NutritionixDaoTest {
    private static final Logger logger = LogManager.getLogger(NutritionixDaoTest.class);

    private String appId;
    private String appKey;
    private NutritionixDao dao;

    @Before
    public void setUp() {
        appId  = System.getenv("nutritionixID");
        appKey = System.getenv("nutritionixKey");
        assertNotNull("Set nutritionixID env var",  appId);
        assertNotNull("Set nutritionixKey env var", appKey);
        dao = new NutritionixDao(appId, appKey);
    }

    @Test
    public void testSearchInstantReturnsCommonAndBranded() {
        NutritionixSearchResponseDto resp = dao.searchInstant("banana");
        assertNotNull(resp);

        // common list must exist and not be empty
        List<CommonItem> common = resp.getCommon();
        assertNotNull(common);
        assertFalse("Expected at least one common item", common.isEmpty());
        logger.info("First common: {} (tagId {})", common.get(0).getFoodName(), common.get(0).getTagId());

        // branded list must exist (may be empty)
        List<BrandedItem> branded = resp.getBranded();
        assertNotNull(branded);
        if (!branded.isEmpty()) {
            logger.info("First branded: {} (nixItemId {})", branded.get(0).getFoodName(), branded.get(0).getNixItemId());
        }
    }

    @Test
    public void testGetCommonConvenience() {
        List<CommonItem> common = dao.getCommon("apple");
        assertNotNull(common);
        assertFalse("getCommon should return at least one apple", common.isEmpty());
        assertTrue(common.get(0).getFoodName().toLowerCase().contains("apple"));
    }

    @Test
    public void testGetBrandedConvenience() {
        List<BrandedItem> branded = dao.getBranded("coke");
        assertNotNull(branded);
        // may legitimately be empty if Nutritionix has no match, but must not be null
    }

    @Test
    public void testFetchByIdReturnsOneFood() {
        // Use a known branded-item ID; replace with a valid one if needed
        String nixItemId = "5c417c43f7b925f079302de7";
        FoodResponse detail = dao.fetchById(nixItemId);

        assertNotNull(detail);
        assertNotNull(detail.getFoods());
        assertFalse("Expected at least one food in response", detail.getFoods().isEmpty());

        FoodsItem item = detail.getFoods().get(0);
        assertEquals("Should preserve nixItemId", nixItemId, item.getNixItemId());
        assertNotNull(item.getFoodName());
        assertTrue("Calories should be non-negative", item.getNfCalories() >= 0);

        logger.info("Fetched [{}] → {} kcal", item.getFoodName(), item.getNfCalories());
    }

    @Test(expected = RuntimeException.class)
    public void testFetchByIdWithInvalidIdThrows() {
        // Should produce a 4xx/5xx and thus a RuntimeException
        dao.fetchById("nonexistent-item-id-xyz");
    }

    @Test
    public void testNaturalNutrientsBanana() {
        // naturalNutrients should give you one FoodsItem describing "banana"
        FoodResponse resp = dao.naturalNutrients("1 banana");
        assertNotNull(resp);
        assertNotNull(resp.getFoods());
        assertFalse("Expected at least one food", resp.getFoods().isEmpty());

        FoodsItem banana = resp.getFoods().get(0);
        assertTrue("Food name should mention banana",
                banana.getFoodName().toLowerCase().contains("banana"));
        assertEquals("Default serving qty from query", 1, banana.getServingQty());
        assertNotNull("Must have at least one alt_measure", banana.getAltMeasures());
        assertTrue("Must have at least one full_nutrient", !banana.getFullNutrients().isEmpty());

        logger.info("Natural nutrients for 1 banana: {} g serving weight, {} kcal",
                banana.getServingWeightGrams(), banana.getNfCalories());
    }

    @Test(expected = RuntimeException.class)
    public void testNaturalNutrientsWithEmptyQueryThrows() {
        // Sending an empty or missing query should error out
        dao.naturalNutrients("");
    }
}
