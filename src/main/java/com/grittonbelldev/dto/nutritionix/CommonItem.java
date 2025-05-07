package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single "common" food item returned from the Nutritionix instant search API.
 * Common food items are generic foods (e.g., "banana", "rice", "chicken breast")
 * rather than branded products.
 *
 * This class maps properties returned by the Nutritionix /search/instant endpoint
 * under the "common" section of the response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonItem {

	// The name of the food item (e.g., "apple", "carrot").
	@JsonProperty("food_name")
	private String foodName;

	// The unit used for serving size (e.g., "medium", "cup").
	@JsonProperty("serving_unit")
	private String servingUnit;

	// The quantity corresponding to the serving unit (e.g., 1, 2).
	@JsonProperty("serving_qty")
	private int servingQty;

	// A short tag used to identify the food (e.g., for categorization).
	@JsonProperty("tag_name")
	private String tagName;

	// Optional field describing the type of common item (usually null or unused).
	@JsonProperty("common_type")
	private Object commonType;

	// Photo metadata associated with the food item.
	@JsonProperty("photo")
	private Photo photo;

	// Optional internal Nutritionix tag identifier.
	@JsonProperty("tag_id")
	private String tagId;

	// The locale of the entry, indicating language or region (e.g., "en_US").
	@JsonProperty("locale")
	private String locale;

	/**
	 * Gets the name of the food item.
	 * @return food name as a String
	 */
	public String getFoodName() {
		return foodName;
	}

	/**
	 * Gets the unit used for serving size.
	 * @return serving unit as a String
	 */
	public String getServingUnit() {
		return servingUnit;
	}

	/**
	 * Gets the numeric quantity of the serving.
	 * @return serving quantity as an int
	 */
	public int getServingQty() {
		return servingQty;
	}

	/**
	 * Gets the tag name associated with the food.
	 * @return tag name as a String
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * Gets the common type of the food item.
	 * @return common type as an Object (nullable)
	 */
	public Object getCommonType() {
		return commonType;
	}

	/**
	 * Gets the photo metadata for the food.
	 * @return a {@link Photo} object
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * Gets the Nutritionix tag ID.
	 * @return tag ID as a String
	 */
	public String getTagId() {
		return tagId;
	}

	/**
	 * Gets the locale for the food entry.
	 * @return locale as a String
	 */
	public String getLocale() {
		return locale;
	}
}
