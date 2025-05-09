package com.grittonbelldev.api;

import com.grittonbelldev.dto.nutritionix.*;
import com.grittonbelldev.service.NutritionixService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Objects;

/**
 * Provides REST API endpoints that proxy Nutritionix search and analysis features.
 *
 * This class handles lookup of common and branded food items, full nutritional
 * details, and supports natural language nutrient parsing.
 *
 * API Endpoints:
 * <ul>
 *     <li>GET    /api/nutritionix/foods?q=term          — returns both common and branded items</li>
 *     <li>GET    /api/nutritionix/foods/common?q=term   — returns only common items</li>
 *     <li>GET    /api/nutritionix/foods/branded?q=term  — returns only branded items</li>
 *     <li>GET    /api/nutritionix/foods/{nixItemId}     — returns full nutrition data for a specific item</li>
 *     <li>POST   /api/nutritionix/foods/nutrients        — parses natural-language queries for nutrition facts</li>
 * </ul>
 *
 * Credentials (appId, appKey) are loaded from ServletContext attributes,
 * which are set during application startup.
 */
@Path("/nutritionix/foods")
@Produces(MediaType.APPLICATION_JSON)
public class NutritionixResource {

    private NutritionixService nutritionixService;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Injects the ServletContext and retrieves Nutritionix credentials.
     * If credentials are missing, this method throws a 503 error.
     *
     * @param ctx the servlet context used to load configuration attributes
     */
    @Context
    public void setServletContext(ServletContext ctx) {
        String appId  = Objects.toString(ctx.getAttribute("nutritionix.appId"), null);
        String appKey = Objects.toString(ctx.getAttribute("nutritionix.appKey"), null);

        if (appId == null || appKey == null) {
            logger.error("Nutritionix credentials missing in ServletContext.");
            throw new WebApplicationException(
                    "Nutritionix API not configured",
                    Response.Status.SERVICE_UNAVAILABLE
            );
        }

        logger.info("Nutritionix credentials loaded from context.");
        // Instantiate service with runtime credentials
        this.nutritionixService = new NutritionixService(appId, appKey);
    }

    /**
     * Performs a full search using the Nutritionix instant search API.
     * Combines both common and branded results into a single DTO.
     *
     * @param q the search query term
     * @return a DTO with separate lists of common and branded results
     */
    @GET
    public NutritionixSearchResponseDto searchAll(
            @QueryParam("q") @DefaultValue("") String q
    ) {
        logger.info("GET /nutritionix/foods?q={}", q);
        try {
            return nutritionixService.searchAll(q);
        } catch (Exception e) {
            logger.error("Error during searchAll: {}", e.getMessage(), e);
            throw new WebApplicationException(
                    "Failed to search Nutritionix: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    /**
     * Performs a search that returns only common food items.
     *
     * @param q the search query term
     * @return a list of common items
     */
    @GET
    @Path("common")
    public List<CommonItem> searchCommon(
            @QueryParam("q") @DefaultValue("") String q
    ) {
        logger.info("GET /nutritionix/foods/common?q={}", q);
        try {
            return nutritionixService.searchCommon(q);
        } catch (Exception e) {
            logger.error("Error during searchCommon: {}", e.getMessage(), e);
            throw new WebApplicationException(
                    "Failed to search common items: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    /**
     * Performs a search that returns only branded food items.
     *
     * @param q the search query term
     * @return a list of branded items
     */
    @GET
    @Path("branded")
    public List<BrandedItem> searchBranded(
            @QueryParam("q") @DefaultValue("") String q
    ) {
        logger.info("GET /nutritionix/foods/branded?q={}", q);
        try {
            return nutritionixService.searchBranded(q);
        } catch (Exception e) {
            logger.error("Error during searchBranded: {}", e.getMessage(), e);
            throw new WebApplicationException(
                    "Failed to search branded items: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    /**
     * Retrieves detailed nutrition data for a single branded item.
     *
     * @param nixItemId the Nutritionix item identifier
     * @return the full {@link FoodsItem} with nutrients and metadata
     */
    @GET
    @Path("{nixItemId}")
    public FoodsItem getById(
            @PathParam("nixItemId") String nixItemId
    ) {
        logger.info("GET /nutritionix/foods/{}", nixItemId);
        try {
            return nutritionixService.fetchById(nixItemId);
        } catch (WebApplicationException e) {
            logger.warn("WebApplicationException for fetchById: {}", e.getMessage(), e);
            throw e; // Preserve any custom HTTP status thrown inside service
        } catch (Exception e) {
            logger.error("Error fetching item by ID: {}", e.getMessage(), e);
            throw new WebApplicationException(
                    "Failed to fetch Nutritionix item: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    /**
     * Parses a natural-language query describing food and portions,
     * and returns estimated nutrients for the described items.
     *
     * @param req a DTO containing the natural language query string
     * @return a list of parsed {@link FoodsItem} entries
     */
    @POST
    @Path("nutrients")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FoodsItem> analyzeNutrients(NutrientsRequestDto req) {
        String q = req.getQuery();
        logger.info("POST /nutritionix/foods/nutrients with query: {}", q);
        if (q == null || q.trim().isEmpty()) {
            logger.warn("Missing query in nutrient analysis request.");
            throw new WebApplicationException("`query` must be provided",
                    Response.Status.BAD_REQUEST);
        }
        try {
            return nutritionixService.naturalNutrients(q);
        } catch (WebApplicationException e) {
            logger.warn("WebApplicationException during nutrient analysis: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Error during nutrient analysis: {}", e.getMessage(), e);
            throw new WebApplicationException("Failed to analyze nutrients: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY);
        }
    }
}
