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
 * <p>
 * Provides methods to:
 * <ul>
 *   <li>Perform instant (type‐ahead) food searches</li>
 *   <li>Retrieve only the “common” or “branded” subsets</li>
 *   <li>Fetch full nutrition details for a given Nutritionix item ID</li>
 *   <li>Get nutrient breakdown for free‐form natural‐language queries</li>
 * </ul>
 * Handles HTTP header injection, response‐status checks, JSON mapping, and
 * proper closing of JAX‐RS Response objects.
 * </p>
 */
public class NutritionixDao {
    private static final Logger logger = LogManager.getLogger(NutritionixDao.class);

    /** Nutritionix instant search endpoint (type-ahead). */
    private static final String INSTANT_URL   = "https://trackapi.nutritionix.com/v2/search/instant";
    /** Nutritionix single-item details endpoint. */
    private static final String ITEM_URL      = "https://trackapi.nutritionix.com/v2/search/item";
    /** Nutritionix natural-language nutrient endpoint. */
    private static final String NATURAL_URL   = "https://trackapi.nutritionix.com/v2/natural/nutrients";

    private final String appId;
    private final String appKey;
    private final Client client = ClientBuilder.newClient();
    private final ObjectMapper mapper;

    /**
     * Construct a DAO with the required Nutritionix credentials.
     *
     * @param appId Nutritionix application ID
     * @param appKey Nutritionix application key
     */
    public NutritionixDao(String appId, String appKey) {
        this.appId  = appId;
        this.appKey = appKey;
        // Configure Jackson to ignore any JSON fields that haven’t been explicitly modeled
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Perform an “instant” (type-ahead) search.
     * <p>
     * Sends a GET to {@value #INSTANT_URL}?query={query} with the X-App-Id/Key headers
     * and maps the JSON into {@link NutritionixSearchResponseDto}.
     * </p>
     *
     * @param query the search term (null → empty string)
     * @return the full search response DTO (common + branded)
     * @throws RuntimeException on non-200 HTTP status, network errors, or JSON parse failures
     */
    public NutritionixSearchResponseDto searchInstant(String query) {
        WebTarget target = client.target(INSTANT_URL)
                .queryParam("query", query == null ? "" : query);

        Response resp = null;
        try {
            resp = target.request(MediaType.APPLICATION_JSON)
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
     * Convenience: return only the “common” items from an instant search.
     *
     * @param q the search term
     * @return list of generic/common foods
     */
    public List<CommonItem> getCommon(String q) {
        return searchInstant(q).getCommon();
    }

    /**
     * Convenience: return only the “branded” items from an instant search.
     *
     * @param q the search term
     * @return list of branded foods
     */
    public List<BrandedItem> getBranded(String q) {
        return searchInstant(q).getBranded();
    }

    /**
     * Fetch full nutrition details for a single Nutritionix item.
     * <p>
     * Sends a GET to {@value #ITEM_URL}?nix_item_id={nixItemId} and maps into {@link FoodResponse}.
     * </p>
     *
     * @param nixItemId the Nutritionix item identifier
     * @return a {@link FoodResponse} (usually contains one element)
     * @throws RuntimeException on non-200 HTTP status, network errors, or JSON parse failures
     */
    public FoodResponse fetchById(String nixItemId) {
        WebTarget target = client.target(ITEM_URL)
                .queryParam("nix_item_id", nixItemId);

        Response resp = null;
        try {
            resp = target.request(MediaType.APPLICATION_JSON)
                    .header("x-app-id",  appId)
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
     * Perform a natural‐language nutrient analysis.
     * <p>
     * Sends a POST to {@value #NATURAL_URL} with JSON body {"query": "..."}
     * and maps the JSON into {@link FoodResponse}.
     * </p>
     *
     * @param query a free‐form description (e.g. "1 banana" or "2 eggs")
     * @return a {@link FoodResponse} (usually contains one element with alt_measures)
     * @throws RuntimeException on non-200 HTTP status, network errors, or JSON parse failures
     */
    public FoodResponse naturalNutrients(String query) {
        WebTarget target = client.target(NATURAL_URL);

        // build JSON body: {"query":"..."}
        Map<String,String> body = new HashMap<>();
        body.put("query", query == null ? "" : query);

        Response resp = null;
        try {
            String payload = mapper.writeValueAsString(body);
            resp = target.request(MediaType.APPLICATION_JSON)
                    .header("x-app-id",  appId)
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
