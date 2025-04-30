package com.grittonbelldev.dto.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The response from Nutritionix’s “instant” search endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutritionixSearchResponseDto {
    @JsonProperty("branded")
    private List<BrandedItem> branded;

    @JsonProperty("common")
    private List<CommonItem> common;

    public List<BrandedItem> getBranded() {
        return branded;
    }
    public void setBranded(List<BrandedItem> branded) {
        this.branded = branded;
    }

    public List<CommonItem> getCommon() {
        return common;
    }
    public void setCommon(List<CommonItem> common) {
        this.common = common;
    }
}
