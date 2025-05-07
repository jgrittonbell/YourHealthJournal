package com.grittonbelldev.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grittonbelldev.dto.nutritionix.BrandedItem;
import com.grittonbelldev.dto.nutritionix.CommonItem;
import com.grittonbelldev.dto.nutritionix.FoodResponse;
import com.grittonbelldev.dto.nutritionix.NutritionixSearchResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO for interacting with the Nutritionix Track API.
 *
 * <p>Supports several types of API calls including instant (type-ahead) searches,
 * branded/common food filtering, item lookups by ID, and natural-language nutrient breakdowns.</p>
 *
 * <p>Handles request header injection, response validation, error propagation,
 * and object mapping using Jackson.</p>
 */
public class NutritionixDao {

    private static final Logger logger = LogManager.getLogger(NutritionixDao.class);

    /** Base URL for instant (type-ahead) search. */
    private static final String INSTANT_URL = "https://trackapi.nutritionix.com/v2/search/instant";

    /** Base URL for retrieving a single item's detailed information. */
    private static final String ITEM_URL = "https://trackapi.nutritionix.com/v2/search/item";

    /** Base URL for free-form, natural-language nutrient analysis. */
    private static final String NATURAL_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";

    private final String appId;
    private final String appKey;
    private final Client client = ClientBuilder.newClient();
    private final ObjectMapper mapper;

    /**
     * Constructs a DAO for Nutritionix with the required application credentials.
     *
     * @param appId the Nutritionix App ID
     * @param appKey the Nutritionix App Key
     */
    public NutritionixDao(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;

        // Configure Jackson to ignore unknown fields in JSON
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Performs an instant (type-ahead) search using the given query string.
     *
     * <p>Returns a result that includes both branded and common food items.</p>
     *
     * @param query the search term (may be null)
     * @return the search result DTO
     * @throws RuntimeException if the HTTP request fails or JSON cannot be parsed
     */
    public NutritionixSearchResponseDto searchInstant(String query) {
        WebTarget target = client.target(INSTANT_URL)
                .queryParam("query", query == null ? "" : query);

        Response resp = null;
        try {
            resp = target.request(MediaType.APPLICATION_JSON)
                    .header("x-app-id", appId)
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
            return mapper.readValue(json, NutritionixSearchResponseDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Nutritionix instant search JSON", e);
        } catch (ProcessingException e) {
            throw new RuntimeException("Error calling Nutritionix instant search API", e);
        } finally {
            if (resp != null) resp.close();
        }
    }

    /**
     * Returns only the list of generic/common food items from an instant search.
     *
     * @param q the search query
     * @return a list of CommonItem entries
     */
    public List<CommonItem> getCommon(String q) {
        return searchInstant(q).getCommon();
    }

    /**
     * Returns only the list of branded food items from an instant search.
     *
     * @param q the search query
     * @return a list of BrandedItem entries
     */
    public List<BrandedItem> getBranded(String q) {
        return searchInstant(q).getBranded();
    }

    /**
     * Retrieves full nutritional details for a specific item by Nutritionix ID.
     *
     * @param nixItemId the Nutritionix item ID
     * @return a FoodResponse containing nutritional data
     * @throws RuntimeException if the request fails or response is malformed
     */
    public FoodResponse fetchById(String nixItemId) {
        WebTarget target = client.target(ITEM_URL)
                .queryParam("nix_item_id", nixItemId);

        Response resp = null;
        try {
            resp = target.request(MediaType.APPLICATION_JSON)
                    .header("x-app-id", appId)
                    .header("x-app-key", appKey)
                    .get();

            if (resp.getStatus() != 200) {
                throw new RuntimeException(
                        "Nutritionix fetch failed: HTTP " +
                                resp.getStatus() + " " + resp.getStatusInfo().getReasonPhrase()
                );
            }

            String json = resp.readEntity(String.class);
            logger.debug("Nutritionix fetch-by-id JSON: {}", json);
            return mapper.readValue(json, FoodResponse.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Nutritionix fetch-by-id JSON", e);
        } catch (ProcessingException e) {
            throw new RuntimeException("Error calling Nutritionix fetch-by-id API", e);
        } finally {
            if (resp != null) resp.close();
        }
    }

    /**
     * Performs a natural-language nutrient analysis using a free-form query.
     *
     * @param query a description such as "1 banana" or "2 eggs and toast"
     * @return a FoodResponse object containing parsed nutrient data
     * @throws RuntimeException if the HTTP call fails or the response cannot be parsed
     */
    public FoodResponse naturalNutrients(String query) {
        WebTarget target = client.target(NATURAL_URL);

        Map<String, String> body = new HashMap<>();
        body.put("query", query == null ? "" : query);

        Response resp = null;
        try {
            String payload = mapper.writeValueAsString(body);

            resp = target.request(MediaType.APPLICATION_JSON)
                    .header("x-app-id", appId)
                    .header("x-app-key", appKey)
                    .post(Entity.json(payload));

            if (resp.getStatus() != 200) {
                throw new RuntimeException(
                        "Nutritionix natural nutrients failed: HTTP " +
                                resp.getStatus() + " " + resp.getStatusInfo().getReasonPhrase()
                );
            }

            String json = resp.readEntity(String.class);
            logger.debug("Nutritionix natural nutrients JSON: {}", json);
            return mapper.readValue(json, FoodResponse.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Nutritionix natural nutrients JSON", e);
        } catch (ProcessingException e) {
            throw new RuntimeException("Error calling Nutritionix natural nutrients API", e);
        } finally {
            if (resp != null) resp.close();
        }
    }
}
