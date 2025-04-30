package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FullNutrientsItem{

	@JsonProperty("value")
	private int value;

	@JsonProperty("attr_id")
	private int attrId;

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}

	public void setAttrId(int attrId){
		this.attrId = attrId;
	}

	public int getAttrId(){
		return attrId;
	}
}