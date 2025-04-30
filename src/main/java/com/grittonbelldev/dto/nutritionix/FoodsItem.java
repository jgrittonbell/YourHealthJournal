package com.grittonbelldev.dto.nutritionix;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodsItem{

	@JsonProperty("food_name")
	private String foodName;

	@JsonProperty("note")
	private Object note;

	@JsonProperty("nf_saturated_fat")
	private Object nfSaturatedFat;

	@JsonProperty("metadata")
	private Metadata metadata;

	@JsonProperty("nf_cholesterol")
	private int nfCholesterol;

	@JsonProperty("nix_brand_id")
	private String nixBrandId;

	@JsonProperty("nf_potassium")
	private int nfPotassium;

	@JsonProperty("nf_total_fat")
	private Object nfTotalFat;

	@JsonProperty("nf_sugars")
	private int nfSugars;

	@JsonProperty("nf_ingredient_statement")
	private String nfIngredientStatement;

	@JsonProperty("nf_protein")
	private int nfProtein;

	@JsonProperty("source")
	private int source;

	@JsonProperty("nix_item_id")
	private String nixItemId;

	@JsonProperty("ndb_no")
	private Object ndbNo;

	@JsonProperty("brick_code")
	private Object brickCode;

	@JsonProperty("serving_unit")
	private String servingUnit;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("alt_measures")
	private Object altMeasures;

	@JsonProperty("tag_id")
	private Object tagId;

	@JsonProperty("nf_p")
	private Object nfP;

	@JsonProperty("nf_metric_qty")
	private int nfMetricQty;

	@JsonProperty("lat")
	private Object lat;

	@JsonProperty("lng")
	private Object lng;

	@JsonProperty("nix_item_name")
	private String nixItemName;

	@JsonProperty("photo")
	private Photo photo;

	@JsonProperty("brand_name")
	private String brandName;

	@JsonProperty("serving_weight_grams")
	private Object servingWeightGrams;

	@JsonProperty("nf_total_carbohydrate")
	private int nfTotalCarbohydrate;

	@JsonProperty("full_nutrients")
	private List<FullNutrientsItem> fullNutrients;

	@JsonProperty("nix_brand_name")
	private String nixBrandName;

	@JsonProperty("serving_qty")
	private int servingQty;

	@JsonProperty("nf_calories")
	private int nfCalories;

	@JsonProperty("nf_metric_uom")
	private String nfMetricUom;

	@JsonProperty("nf_sodium")
	private int nfSodium;

	@JsonProperty("class_code")
	private Object classCode;

	@JsonProperty("nf_dietary_fiber")
	private int nfDietaryFiber;

	public void setFoodName(String foodName){
		this.foodName = foodName;
	}

	public String getFoodName(){
		return foodName;
	}

	public void setNote(Object note){
		this.note = note;
	}

	public Object getNote(){
		return note;
	}

	public void setNfSaturatedFat(Object nfSaturatedFat){
		this.nfSaturatedFat = nfSaturatedFat;
	}

	public Object getNfSaturatedFat(){
		return nfSaturatedFat;
	}

	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public void setNfCholesterol(int nfCholesterol){
		this.nfCholesterol = nfCholesterol;
	}

	public int getNfCholesterol(){
		return nfCholesterol;
	}

	public void setNixBrandId(String nixBrandId){
		this.nixBrandId = nixBrandId;
	}

	public String getNixBrandId(){
		return nixBrandId;
	}

	public void setNfPotassium(int nfPotassium){
		this.nfPotassium = nfPotassium;
	}

	public int getNfPotassium(){
		return nfPotassium;
	}

	public void setNfTotalFat(Object nfTotalFat){
		this.nfTotalFat = nfTotalFat;
	}

	public Object getNfTotalFat(){
		return nfTotalFat;
	}

	public void setNfSugars(int nfSugars){
		this.nfSugars = nfSugars;
	}

	public int getNfSugars(){
		return nfSugars;
	}

	public void setNfIngredientStatement(String nfIngredientStatement){
		this.nfIngredientStatement = nfIngredientStatement;
	}

	public String getNfIngredientStatement(){
		return nfIngredientStatement;
	}

	public void setNfProtein(int nfProtein){
		this.nfProtein = nfProtein;
	}

	public int getNfProtein(){
		return nfProtein;
	}

	public void setSource(int source){
		this.source = source;
	}

	public int getSource(){
		return source;
	}

	public void setNixItemId(String nixItemId){
		this.nixItemId = nixItemId;
	}

	public String getNixItemId(){
		return nixItemId;
	}

	public void setNdbNo(Object ndbNo){
		this.ndbNo = ndbNo;
	}

	public Object getNdbNo(){
		return ndbNo;
	}

	public void setBrickCode(Object brickCode){
		this.brickCode = brickCode;
	}

	public Object getBrickCode(){
		return brickCode;
	}

	public void setServingUnit(String servingUnit){
		this.servingUnit = servingUnit;
	}

	public String getServingUnit(){
		return servingUnit;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setAltMeasures(Object altMeasures){
		this.altMeasures = altMeasures;
	}

	public Object getAltMeasures(){
		return altMeasures;
	}

	public void setTagId(Object tagId){
		this.tagId = tagId;
	}

	public Object getTagId(){
		return tagId;
	}

	public void setNfP(Object nfP){
		this.nfP = nfP;
	}

	public Object getNfP(){
		return nfP;
	}

	public void setNfMetricQty(int nfMetricQty){
		this.nfMetricQty = nfMetricQty;
	}

	public int getNfMetricQty(){
		return nfMetricQty;
	}

	public void setLat(Object lat){
		this.lat = lat;
	}

	public Object getLat(){
		return lat;
	}

	public void setLng(Object lng){
		this.lng = lng;
	}

	public Object getLng(){
		return lng;
	}

	public void setNixItemName(String nixItemName){
		this.nixItemName = nixItemName;
	}

	public String getNixItemName(){
		return nixItemName;
	}

	public void setPhoto(Photo photo){
		this.photo = photo;
	}

	public Photo getPhoto(){
		return photo;
	}

	public void setBrandName(String brandName){
		this.brandName = brandName;
	}

	public String getBrandName(){
		return brandName;
	}

	public void setServingWeightGrams(Object servingWeightGrams){
		this.servingWeightGrams = servingWeightGrams;
	}

	public Object getServingWeightGrams(){
		return servingWeightGrams;
	}

	public void setNfTotalCarbohydrate(int nfTotalCarbohydrate){
		this.nfTotalCarbohydrate = nfTotalCarbohydrate;
	}

	public int getNfTotalCarbohydrate(){
		return nfTotalCarbohydrate;
	}

	public void setFullNutrients(List<FullNutrientsItem> fullNutrients){
		this.fullNutrients = fullNutrients;
	}

	public List<FullNutrientsItem> getFullNutrients(){
		return fullNutrients;
	}

	public void setNixBrandName(String nixBrandName){
		this.nixBrandName = nixBrandName;
	}

	public String getNixBrandName(){
		return nixBrandName;
	}

	public void setServingQty(int servingQty){
		this.servingQty = servingQty;
	}

	public int getServingQty(){
		return servingQty;
	}

	public void setNfCalories(int nfCalories){
		this.nfCalories = nfCalories;
	}

	public int getNfCalories(){
		return nfCalories;
	}

	public void setNfMetricUom(String nfMetricUom){
		this.nfMetricUom = nfMetricUom;
	}

	public String getNfMetricUom(){
		return nfMetricUom;
	}

	public void setNfSodium(int nfSodium){
		this.nfSodium = nfSodium;
	}

	public int getNfSodium(){
		return nfSodium;
	}

	public void setClassCode(Object classCode){
		this.classCode = classCode;
	}

	public Object getClassCode(){
		return classCode;
	}

	public void setNfDietaryFiber(int nfDietaryFiber){
		this.nfDietaryFiber = nfDietaryFiber;
	}

	public int getNfDietaryFiber(){
		return nfDietaryFiber;
	}
}