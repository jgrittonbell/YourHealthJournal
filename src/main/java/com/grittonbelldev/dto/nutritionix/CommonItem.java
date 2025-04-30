package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonItem {

	@JsonProperty("food_name")
	private String foodName;

	@JsonProperty("serving_unit")
	private String servingUnit;

	@JsonProperty("serving_qty")
	private int servingQty;

	@JsonProperty("tag_name")
	private String tagName;

	@JsonProperty("common_type")
	private Object commonType;

	@JsonProperty("photo")
	private Photo photo;

	@JsonProperty("tag_id")
	private String tagId;

	@JsonProperty("locale")
	private String locale;

	public String getFoodName() {
		return foodName;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public int getServingQty() {
		return servingQty;
	}

	public String getTagName() {
		return tagName;
	}

	public Object getCommonType() {
		return commonType;
	}

	public Photo getPhoto() {
		return photo;
	}

	public String getTagId() {
		return tagId;
	}

	public String getLocale() {
		return locale;
	}
}
