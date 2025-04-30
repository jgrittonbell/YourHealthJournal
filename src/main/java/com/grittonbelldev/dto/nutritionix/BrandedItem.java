package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandedItem {

	@JsonProperty("food_name")
	private String foodName;

	@JsonProperty("serving_unit")
	private String servingUnit;

	@JsonProperty("nix_brand_id")
	private String nixBrandId;

	@JsonProperty("brand_name_item_name")
	private String brandNameItemName;

	@JsonProperty("serving_qty")
	private int servingQty;

	@JsonProperty("nf_calories")
	private int nfCalories;

	@JsonProperty("photo")
	private Photo photo;

	@JsonProperty("brand_name")
	private String brandName;

	@JsonProperty("region")
	private int region;

	@JsonProperty("locale")
	private String locale;

	@JsonProperty("brand_type")
	private int brandType;

	@JsonProperty("nix_item_id")
	private String nixItemId;

	public String getFoodName() {
		return foodName;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public String getNixBrandId() {
		return nixBrandId;
	}

	public String getBrandNameItemName() {
		return brandNameItemName;
	}

	public int getServingQty() {
		return servingQty;
	}

	public int getNfCalories() {
		return nfCalories;
	}

	public Photo getPhoto() {
		return photo;
	}

	public String getBrandName() {
		return brandName;
	}

	public int getRegion() {
		return region;
	}

	public String getLocale() {
		return locale;
	}

	public int getBrandType() {
		return brandType;
	}

	public String getNixItemId() {
		return nixItemId;
	}
}
