package com.grittonbelldev.dto.nutritionix;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single food item returned by the Nutritionix API's natural language or instant search endpoints.
 * This class maps a large variety of nutritional and metadata fields as returned in the JSON payload.
 *
 * Fields include basic nutrition info (macros, calories, fiber, etc.), branding information,
 * serving details, and additional metadata such as source, update time, and measurement units.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodsItem {

	// The name of the food (e.g. "banana", "oatmeal")
	@JsonProperty("food_name")
	private String foodName;

	// Any note included in the result (such as user notes or clarifications)
	@JsonProperty("note")
	private Object note;

	// The amount of saturated fat (may be null or non-standard)
	@JsonProperty("nf_saturated_fat")
	private Object nfSaturatedFat;

	// Metadata object containing additional structured information
	@JsonProperty("metadata")
	private Metadata metadata;

	// Cholesterol content in milligrams
	@JsonProperty("nf_cholesterol")
	private int nfCholesterol;

	// Nutritionix brand ID
	@JsonProperty("nix_brand_id")
	private String nixBrandId;

	// Potassium content in milligrams
	@JsonProperty("nf_potassium")
	private int nfPotassium;

	// Total fat content (may be a decimal or formatted string)
	@JsonProperty("nf_total_fat")
	private Object nfTotalFat;

	// Sugar content in grams
	@JsonProperty("nf_sugars")
	private int nfSugars;

	// Ingredient statement provided by the brand or Nutritionix
	@JsonProperty("nf_ingredient_statement")
	private String nfIngredientStatement;

	// Protein content in grams
	@JsonProperty("nf_protein")
	private int nfProtein;

	// Integer code indicating the source of the data
	@JsonProperty("source")
	private int source;

	// Unique item identifier from Nutritionix
	@JsonProperty("nix_item_id")
	private String nixItemId;

	// USDA National Database ID (if available)
	@JsonProperty("ndb_no")
	private Object ndbNo;

	// Optional field indicating brick code classification
	@JsonProperty("brick_code")
	private Object brickCode;

	// Unit in which the serving is measured (e.g., "cup", "g")
	@JsonProperty("serving_unit")
	private String servingUnit;

	// ISO 8601 date string indicating the last update time
	@JsonProperty("updated_at")
	private String updatedAt;

	// Alternative serving sizes or measurements
	@JsonProperty("alt_measures")
	private Object altMeasures;

	// Tag identifier for categorization
	@JsonProperty("tag_id")
	private Object tagId;

	// Phosphorus content (may be missing or represented as text)
	@JsonProperty("nf_p")
	private Object nfP;

	// Quantity in metric units (e.g., grams)
	@JsonProperty("nf_metric_qty")
	private int nfMetricQty;

	// Latitude and longitude fields, likely included if location-tracked
	@JsonProperty("lat")
	private Object lat;

	@JsonProperty("lng")
	private Object lng;

	// Alternate item name used by Nutritionix
	@JsonProperty("nix_item_name")
	private String nixItemName;

	// Photo object containing image URLs and flags
	@JsonProperty("photo")
	private Photo photo;

	// Brand name (e.g., "Kellogg’s", "Chobani")
	@JsonProperty("brand_name")
	private String brandName;

	// Weight of the serving in grams (can be null or approximate)
	@JsonProperty("serving_weight_grams")
	private Object servingWeightGrams;

	// Total carbohydrate content in grams
	@JsonProperty("nf_total_carbohydrate")
	private int nfTotalCarbohydrate;

	// List of detailed nutrient attributes with attr_id and values
	@JsonProperty("full_nutrients")
	private List<FullNutrientsItem> fullNutrients;

	// Alternate brand name used in some responses
	@JsonProperty("nix_brand_name")
	private String nixBrandName;

	// Number of servings
	@JsonProperty("serving_qty")
	private int servingQty;

	// Caloric content of the item
	@JsonProperty("nf_calories")
	private int nfCalories;

	// Metric unit of measurement (e.g., "g", "ml")
	@JsonProperty("nf_metric_uom")
	private String nfMetricUom;

	// Sodium content in milligrams
	@JsonProperty("nf_sodium")
	private int nfSodium;

	// Classification code provided for grouping
	@JsonProperty("class_code")
	private Object classCode;

	// Dietary fiber content in grams
	@JsonProperty("nf_dietary_fiber")
	private int nfDietaryFiber;

	// Getters and setters follow, required for Jackson to deserialize properly

	/**
	 * Sets food name.
	 *
	 * @param foodName the food name
	 */
	public void setFoodName(String foodName) { this.foodName = foodName; }

	/**
	 * Gets food name.
	 *
	 * @return the food name
	 */
	public String getFoodName() { return foodName; }

	/**
	 * Sets note.
	 *
	 * @param note the note
	 */
	public void setNote(Object note) { this.note = note; }

	/**
	 * Gets note.
	 *
	 * @return the note
	 */
	public Object getNote() { return note; }

	/**
	 * Sets nf saturated fat.
	 *
	 * @param nfSaturatedFat the nf saturated fat
	 */
	public void setNfSaturatedFat(Object nfSaturatedFat) { this.nfSaturatedFat = nfSaturatedFat; }

	/**
	 * Gets nf saturated fat.
	 *
	 * @return the nf saturated fat
	 */
	public Object getNfSaturatedFat() { return nfSaturatedFat; }

	/**
	 * Sets metadata.
	 *
	 * @param metadata the metadata
	 */
	public void setMetadata(Metadata metadata) { this.metadata = metadata; }

	/**
	 * Gets metadata.
	 *
	 * @return the metadata
	 */
	public Metadata getMetadata() { return metadata; }

	/**
	 * Sets nf cholesterol.
	 *
	 * @param nfCholesterol the nf cholesterol
	 */
	public void setNfCholesterol(int nfCholesterol) { this.nfCholesterol = nfCholesterol; }

	/**
	 * Gets nf cholesterol.
	 *
	 * @return the nf cholesterol
	 */
	public int getNfCholesterol() { return nfCholesterol; }

	/**
	 * Sets nix brand id.
	 *
	 * @param nixBrandId the nix brand id
	 */
	public void setNixBrandId(String nixBrandId) { this.nixBrandId = nixBrandId; }

	/**
	 * Gets nix brand id.
	 *
	 * @return the nix brand id
	 */
	public String getNixBrandId() { return nixBrandId; }

	/**
	 * Sets nf potassium.
	 *
	 * @param nfPotassium the nf potassium
	 */
	public void setNfPotassium(int nfPotassium) { this.nfPotassium = nfPotassium; }

	/**
	 * Gets nf potassium.
	 *
	 * @return the nf potassium
	 */
	public int getNfPotassium() { return nfPotassium; }

	/**
	 * Sets nf total fat.
	 *
	 * @param nfTotalFat the nf total fat
	 */
	public void setNfTotalFat(Object nfTotalFat) { this.nfTotalFat = nfTotalFat; }

	/**
	 * Gets nf total fat.
	 *
	 * @return the nf total fat
	 */
	public Object getNfTotalFat() { return nfTotalFat; }

	/**
	 * Sets nf sugars.
	 *
	 * @param nfSugars the nf sugars
	 */
	public void setNfSugars(int nfSugars) { this.nfSugars = nfSugars; }

	/**
	 * Gets nf sugars.
	 *
	 * @return the nf sugars
	 */
	public int getNfSugars() { return nfSugars; }

	/**
	 * Sets nf ingredient statement.
	 *
	 * @param nfIngredientStatement the nf ingredient statement
	 */
	public void setNfIngredientStatement(String nfIngredientStatement) { this.nfIngredientStatement = nfIngredientStatement; }

	/**
	 * Gets nf ingredient statement.
	 *
	 * @return the nf ingredient statement
	 */
	public String getNfIngredientStatement() { return nfIngredientStatement; }

	/**
	 * Sets nf protein.
	 *
	 * @param nfProtein the nf protein
	 */
	public void setNfProtein(int nfProtein) { this.nfProtein = nfProtein; }

	/**
	 * Gets nf protein.
	 *
	 * @return the nf protein
	 */
	public int getNfProtein() { return nfProtein; }

	/**
	 * Sets source.
	 *
	 * @param source the source
	 */
	public void setSource(int source) { this.source = source; }

	/**
	 * Gets source.
	 *
	 * @return the source
	 */
	public int getSource() { return source; }

	/**
	 * Sets nix item id.
	 *
	 * @param nixItemId the nix item id
	 */
	public void setNixItemId(String nixItemId) { this.nixItemId = nixItemId; }

	/**
	 * Gets nix item id.
	 *
	 * @return the nix item id
	 */
	public String getNixItemId() { return nixItemId; }

	/**
	 * Sets ndb no.
	 *
	 * @param ndbNo the ndb no
	 */
	public void setNdbNo(Object ndbNo) { this.ndbNo = ndbNo; }

	/**
	 * Gets ndb no.
	 *
	 * @return the ndb no
	 */
	public Object getNdbNo() { return ndbNo; }

	/**
	 * Sets brick code.
	 *
	 * @param brickCode the brick code
	 */
	public void setBrickCode(Object brickCode) { this.brickCode = brickCode; }

	/**
	 * Gets brick code.
	 *
	 * @return the brick code
	 */
	public Object getBrickCode() { return brickCode; }

	/**
	 * Sets serving unit.
	 *
	 * @param servingUnit the serving unit
	 */
	public void setServingUnit(String servingUnit) { this.servingUnit = servingUnit; }

	/**
	 * Gets serving unit.
	 *
	 * @return the serving unit
	 */
	public String getServingUnit() { return servingUnit; }

	/**
	 * Sets updated at.
	 *
	 * @param updatedAt the updated at
	 */
	public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

	/**
	 * Gets updated at.
	 *
	 * @return the updated at
	 */
	public String getUpdatedAt() { return updatedAt; }

	/**
	 * Sets alt measures.
	 *
	 * @param altMeasures the alt measures
	 */
	public void setAltMeasures(Object altMeasures) { this.altMeasures = altMeasures; }

	/**
	 * Gets alt measures.
	 *
	 * @return the alt measures
	 */
	public Object getAltMeasures() { return altMeasures; }

	/**
	 * Sets tag id.
	 *
	 * @param tagId the tag id
	 */
	public void setTagId(Object tagId) { this.tagId = tagId; }

	/**
	 * Gets tag id.
	 *
	 * @return the tag id
	 */
	public Object getTagId() { return tagId; }

	/**
	 * Sets nf p.
	 *
	 * @param nfP the nf p
	 */
	public void setNfP(Object nfP) { this.nfP = nfP; }

	/**
	 * Gets nf p.
	 *
	 * @return the nf p
	 */
	public Object getNfP() { return nfP; }

	/**
	 * Sets nf metric qty.
	 *
	 * @param nfMetricQty the nf metric qty
	 */
	public void setNfMetricQty(int nfMetricQty) { this.nfMetricQty = nfMetricQty; }

	/**
	 * Gets nf metric qty.
	 *
	 * @return the nf metric qty
	 */
	public int getNfMetricQty() { return nfMetricQty; }

	/**
	 * Sets lat.
	 *
	 * @param lat the lat
	 */
	public void setLat(Object lat) { this.lat = lat; }

	/**
	 * Gets lat.
	 *
	 * @return the lat
	 */
	public Object getLat() { return lat; }

	/**
	 * Sets lng.
	 *
	 * @param lng the lng
	 */
	public void setLng(Object lng) { this.lng = lng; }

	/**
	 * Gets lng.
	 *
	 * @return the lng
	 */
	public Object getLng() { return lng; }

	/**
	 * Sets nix item name.
	 *
	 * @param nixItemName the nix item name
	 */
	public void setNixItemName(String nixItemName) { this.nixItemName = nixItemName; }

	/**
	 * Gets nix item name.
	 *
	 * @return the nix item name
	 */
	public String getNixItemName() { return nixItemName; }

	/**
	 * Sets photo.
	 *
	 * @param photo the photo
	 */
	public void setPhoto(Photo photo) { this.photo = photo; }

	/**
	 * Gets photo.
	 *
	 * @return the photo
	 */
	public Photo getPhoto() { return photo; }

	/**
	 * Sets brand name.
	 *
	 * @param brandName the brand name
	 */
	public void setBrandName(String brandName) { this.brandName = brandName; }

	/**
	 * Gets brand name.
	 *
	 * @return the brand name
	 */
	public String getBrandName() { return brandName; }

	/**
	 * Sets serving weight grams.
	 *
	 * @param servingWeightGrams the serving weight grams
	 */
	public void setServingWeightGrams(Object servingWeightGrams) { this.servingWeightGrams = servingWeightGrams; }

	/**
	 * Gets serving weight grams.
	 *
	 * @return the serving weight grams
	 */
	public Object getServingWeightGrams() { return servingWeightGrams; }

	/**
	 * Sets nf total carbohydrate.
	 *
	 * @param nfTotalCarbohydrate the nf total carbohydrate
	 */
	public void setNfTotalCarbohydrate(int nfTotalCarbohydrate) { this.nfTotalCarbohydrate = nfTotalCarbohydrate; }

	/**
	 * Gets nf total carbohydrate.
	 *
	 * @return the nf total carbohydrate
	 */
	public int getNfTotalCarbohydrate() { return nfTotalCarbohydrate; }

	/**
	 * Sets full nutrients.
	 *
	 * @param fullNutrients the full nutrients
	 */
	public void setFullNutrients(List<FullNutrientsItem> fullNutrients) { this.fullNutrients = fullNutrients; }

	/**
	 * Gets full nutrients.
	 *
	 * @return the full nutrients
	 */
	public List<FullNutrientsItem> getFullNutrients() { return fullNutrients; }

	/**
	 * Sets nix brand name.
	 *
	 * @param nixBrandName the nix brand name
	 */
	public void setNixBrandName(String nixBrandName) { this.nixBrandName = nixBrandName; }

	/**
	 * Gets nix brand name.
	 *
	 * @return the nix brand name
	 */
	public String getNixBrandName() { return nixBrandName; }

	/**
	 * Sets serving qty.
	 *
	 * @param servingQty the serving qty
	 */
	public void setServingQty(int servingQty) { this.servingQty = servingQty; }

	/**
	 * Gets serving qty.
	 *
	 * @return the serving qty
	 */
	public int getServingQty() { return servingQty; }

	/**
	 * Sets nf calories.
	 *
	 * @param nfCalories the nf calories
	 */
	public void setNfCalories(int nfCalories) { this.nfCalories = nfCalories; }

	/**
	 * Gets nf calories.
	 *
	 * @return the nf calories
	 */
	public int getNfCalories() { return nfCalories; }

	/**
	 * Sets nf metric uom.
	 *
	 * @param nfMetricUom the nf metric uom
	 */
	public void setNfMetricUom(String nfMetricUom) { this.nfMetricUom = nfMetricUom; }

	/**
	 * Gets nf metric uom.
	 *
	 * @return the nf metric uom
	 */
	public String getNfMetricUom() { return nfMetricUom; }

	/**
	 * Sets nf sodium.
	 *
	 * @param nfSodium the nf sodium
	 */
	public void setNfSodium(int nfSodium) { this.nfSodium = nfSodium; }

	/**
	 * Gets nf sodium.
	 *
	 * @return the nf sodium
	 */
	public int getNfSodium() { return nfSodium; }

	/**
	 * Sets class code.
	 *
	 * @param classCode the class code
	 */
	public void setClassCode(Object classCode) { this.classCode = classCode; }

	/**
	 * Gets class code.
	 *
	 * @return the class code
	 */
	public Object getClassCode() { return classCode; }

	/**
	 * Sets nf dietary fiber.
	 *
	 * @param nfDietaryFiber the nf dietary fiber
	 */
	public void setNfDietaryFiber(int nfDietaryFiber) { this.nfDietaryFiber = nfDietaryFiber; }

	/**
	 * Gets nf dietary fiber.
	 *
	 * @return the nf dietary fiber
	 */
	public int getNfDietaryFiber() { return nfDietaryFiber; }
}
