package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a branded food item returned by the Nutritionix instant search API.
 * Branded items include commercial products from known manufacturers (e.g., "Cheerios", "Yoplait Yogurt").
 * This class maps key properties returned under the "branded" section of the /search/instant endpoint response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandedItem {

	// The name of the food item (e.g., "Classic Hummus").
	@JsonProperty("food_name")
	private String foodName;

	// The unit in which the food is served (e.g., "container", "bar").
	@JsonProperty("serving_unit")
	private String servingUnit;

	// The Nutritionix internal brand ID associated with the food.
	@JsonProperty("nix_brand_id")
	private String nixBrandId;

	// A combined string showing both the brand and item name for display.
	@JsonProperty("brand_name_item_name")
	private String brandNameItemName;

	// The numeric quantity of the serving (e.g., 1, 2).
	@JsonProperty("serving_qty")
	private int servingQty;

	// The number of calories in one serving of the item.
	@JsonProperty("nf_calories")
	private int nfCalories;

	// Photo metadata associated with the branded item.
	@JsonProperty("photo")
	private Photo photo;

	// The name of the brand (e.g., "Sabra", "General Mills").
	@JsonProperty("brand_name")
	private String brandName;

	// The region code associated with the brand (used for filtering by market).
	@JsonProperty("region")
	private int region;

	// The locale in which the item is categorized (e.g., "en_US").
	@JsonProperty("locale")
	private String locale;

	// Indicates the type of brand (e.g., 1 for manufacturer, 2 for distributor).
	@JsonProperty("brand_type")
	private int brandType;

	// The unique Nutritionix item ID used for fetching detailed nutrition data.
	@JsonProperty("nix_item_id")
	private String nixItemId;

	/**
	 * Gets the name of the food item.
	 * @return food name as a String
	 */
	public String getFoodName() {
		return foodName;
	}

	/**
	 * Gets the unit used for the serving size.
	 * @return serving unit as a String
	 */
	public String getServingUnit() {
		return servingUnit;
	}

	/**
	 * Gets the Nutritionix brand ID.
	 * @return brand ID as a String
	 */
	public String getNixBrandId() {
		return nixBrandId;
	}

	/**
	 * Gets the combined brand and item name string.
	 * @return full display name as a String
	 */
	public String getBrandNameItemName() {
		return brandNameItemName;
	}

	/**
	 * Gets the numeric quantity of the serving.
	 * @return serving quantity as an int
	 */
	public int getServingQty() {
		return servingQty;
	}

	/**
	 * Gets the calorie count per serving.
	 * @return calorie count as an int
	 */
	public int getNfCalories() {
		return nfCalories;
	}

	/**
	 * Gets the photo metadata.
	 * @return {@link Photo} object for image data
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * Gets the name of the brand.
	 * @return brand name as a String
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * Gets the regional identifier.
	 * @return region as an int
	 */
	public int getRegion() {
		return region;
	}

	/**
	 * Gets the locale string for the item.
	 * @return locale as a String
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Gets the type of brand.
	 * @return brand type as an int
	 */
	public int getBrandType() {
		return brandType;
	}

	/**
	 * Gets the unique Nutritionix item ID.
	 * @return item ID as a String
	 */
	public String getNixItemId() {
		return nixItemId;
	}
}
