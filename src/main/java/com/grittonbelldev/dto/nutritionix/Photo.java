package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo{

	@JsonProperty("is_user_uploaded")
	private boolean isUserUploaded;

	@JsonProperty("thumb")
	private String thumb;

	@JsonProperty("highres")
	private Object highres;

	public void setIsUserUploaded(boolean isUserUploaded){
		this.isUserUploaded = isUserUploaded;
	}

	public boolean isIsUserUploaded(){
		return isUserUploaded;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setHighres(Object highres){
		this.highres = highres;
	}

	public Object getHighres(){
		return highres;
	}
}