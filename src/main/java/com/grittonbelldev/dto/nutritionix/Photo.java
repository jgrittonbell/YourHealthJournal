package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {

	@JsonProperty("thumb")
	private String thumb;

	@JsonProperty("highres")
	private Object highres;

	@JsonProperty("is_user_uploaded")
	private boolean isUserUploaded;

	public String getThumb() {
		return thumb;
	}

	public boolean isUserUploaded() {
		return isUserUploaded;
	}

	public Object getHighres() {
		return highres;
	}
}
