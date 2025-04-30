package com.grittonbelldev.dto.nutritionix;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodResponse{

	@JsonProperty("foods")
	private List<FoodsItem> foods;

	public void setFoods(List<FoodsItem> foods){
		this.foods = foods;
	}

	public List<FoodsItem> getFoods(){
		return foods;
	}
}