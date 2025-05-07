package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an individual nutrient entry within the {@code full_nutrients}
 * list returned by Nutritionix food detail responses.
 *
 * <p>
 * Each nutrient is identified by an {@code attr_id} (attribute ID) and a
 * corresponding numeric {@code value}. These are used to map specific
 * nutrient types such as protein, fat, fiber, etc.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullNutrientsItem {

	/** The numeric value of the nutrient (e.g., amount in grams or milligrams). */
	@JsonProperty("value")
	private int value;

	/** The Nutritionix-defined attribute ID for this nutrient. */
	@JsonProperty("attr_id")
	private int attrId;

	/**
	 * Set the nutrient value.
	 *
	 * @param value numeric value of the nutrient
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Get the nutrient value.
	 *
	 * @return numeric value of the nutrient
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Set the Nutritionix attribute ID.
	 *
	 * @param attrId the unique attribute ID for the nutrient type
	 */
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}

	/**
	 * Get the Nutritionix attribute ID.
	 *
	 * @return attribute ID representing the nutrient type
	 */
	public int getAttrId() {
		return attrId;
	}
}
