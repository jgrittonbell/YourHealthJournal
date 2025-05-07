package com.grittonbelldev.dto.nutritionix;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the root object returned by the Nutritionix API when performing a natural language
 * query for nutrients (typically using the POST /v2/natural/nutrients endpoint).
 *
 * This class wraps a list of parsed food entries under the "foods" property.
 * Each entry in the list is mapped to a {@link FoodsItem}, which contains the detailed
 * nutritional information for an individual food item.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodResponse {

	// A list of food items returned by the Nutritionix API in response to a nutrient query.
	@JsonProperty("foods")
	private List<FoodsItem> foods;

	/**
	 * Sets the list of food items parsed from the API response.
	 *
	 * @param foods A list of {@link FoodsItem} objects
	 */
	public void setFoods(List<FoodsItem> foods) {
		this.foods = foods;
	}

	/**
	 * Gets the list of food items contained in the response.
	 *
	 * @return A list of {@link FoodsItem} objects
	 */
	public List<FoodsItem> getFoods() {
		return foods;
	}
}
