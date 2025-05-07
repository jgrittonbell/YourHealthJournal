package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a photo object returned by the Nutritionix API.
 *
 * <p>
 * Includes URLs for thumbnail and high-resolution images, and a flag indicating
 * whether the image was uploaded by a user. The structure is used as part of
 * branded or common food item responses.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {

	/** Indicates whether the photo was uploaded by a user. */
	@JsonProperty("is_user_uploaded")
	private boolean isUserUploaded;

	/** URL to the thumbnail-sized image. */
	@JsonProperty("thumb")
	private String thumb;

	/** A reference to the high-resolution image. Type is Object due to API variability. */
	@JsonProperty("highres")
	private Object highres;

	/**
	 * Set the user-uploaded flag.
	 *
	 * @param isUserUploaded true if uploaded by a user; otherwise false
	 */
	public void setIsUserUploaded(boolean isUserUploaded) {
		this.isUserUploaded = isUserUploaded;
	}

	/**
	 * Check if the photo was uploaded by a user.
	 *
	 * @return true if user-uploaded; otherwise false
	 */
	public boolean isIsUserUploaded() {
		return isUserUploaded;
	}

	/**
	 * Set the thumbnail image URL.
	 *
	 * @param thumb the URL of the thumbnail image
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	/**
	 * Get the thumbnail image URL.
	 *
	 * @return the thumbnail URL as a string
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * Set the high-resolution image data or reference.
	 *
	 * @param highres the high-resolution image object
	 */
	public void setHighres(Object highres) {
		this.highres = highres;
	}

	/**
	 * Get the high-resolution image data or reference.
	 *
	 * @return the high-resolution image as an object
	 */
	public Object getHighres() {
		return highres;
	}
}
