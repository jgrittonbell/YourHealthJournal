package com.grittonbelldev.persistence;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grittonbelldev.dto.nutritionix.BrandedItem;
import com.grittonbelldev.dto.nutritionix.CommonItem;
import com.grittonbelldev.dto.nutritionix.NutritionixSearchResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class NutritionixDao {
    private static final Logger logger = LogManager.getLogger(NutritionixDao.class);
    private static final String BASE_URL = "https://trackapi.nutritionix.com/v2/search/instant";

    private final String appId;
    private final String appKey;
    private final Client client = ClientBuilder.newClient();
    private final ObjectMapper mapper;

    public NutritionixDao(String appId, String appKey) {
        this.appId  = appId;
        this.appKey = appKey;
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public NutritionixSearchResponseDto searchInstant(String query) {
        WebTarget target = client
                .target(BASE_URL)
                .queryParam("query", query == null ? "" : query);

        Response resp = target.request(MediaType.APPLICATION_JSON)
                .header("x-app-id",  appId)
                .header("x-app-key", appKey)
                .get();

        if (resp.getStatus() != 200) {
            throw new RuntimeException(
                    "Nutritionix instant search failed: HTTP " +
                            resp.getStatus() + " " + resp.getStatusInfo().getReasonPhrase()
            );
        }

        String json = resp.readEntity(String.class);
        logger.debug("Nutritionix instant search JSON: {}", json);

        try {
            return mapper.readValue(json, NutritionixSearchResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize Nutritionix JSON", e);
        }
    }

    public List<CommonItem> getCommon(String q) {
        return searchInstant(q).getCommon();
    }

    public List<BrandedItem> getBranded(String q) {
        return searchInstant(q).getBranded();
    }
}
