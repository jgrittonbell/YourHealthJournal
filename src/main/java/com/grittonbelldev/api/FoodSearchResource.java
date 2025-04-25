package com.grittonbelldev.api;

import com.grittonbelldev.dto.nutritionix.BrandedItem;
import com.grittonbelldev.dto.nutritionix.CommonItem;
import com.grittonbelldev.dto.nutritionix.NutritionixSearchResponseDto;
import com.grittonbelldev.service.NutritionixService;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Objects;

/**
 * Exposes Nutritionix instant‐search functionality:
 *   GET  /api/nutritionix/foods?q=…         ⇒ full DTO (common + branded)
 *   GET  /api/nutritionix/foods/common?q=…  ⇒ just common items
 *   GET  /api/nutritionix/foods/branded?q=… ⇒ just branded items
 *
 * Credentials are looked up once from the ServletContext.
 */
@Path("/nutritionix/foods")
@Produces(MediaType.APPLICATION_JSON)
public class FoodSearchResource {

    private NutritionixService nutritionixService;

    /**
     * JAX-RS will call this once, giving you the ServletContext
     * so you can pull out your appId/appKey and wire up the service.
     */
    @Context
    public void setServletContext(ServletContext ctx) {
        String appId  = Objects.toString(ctx.getAttribute("nutritionix.appId"), null);
        String appKey = Objects.toString(ctx.getAttribute("nutritionix.appKey"), null);

        if (appId == null || appKey == null) {
            throw new WebApplicationException(
                    "Nutritionix API not configured",
                    Response.Status.SERVICE_UNAVAILABLE
            );
        }

        this.nutritionixService = new NutritionixService(appId, appKey);
    }

    /**
     * Full instant‐search: common + branded in one DTO.
     * GET  /api/nutritionix/foods?q=banana
     */
    @GET
    public NutritionixSearchResponseDto searchAll(
            @QueryParam("q") @DefaultValue("") String q
    ) {
        try {
            return nutritionixService.searchAll(q);
        } catch (Exception e) {
            throw new WebApplicationException(
                    "Failed to search Nutritionix: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    /**
     * Only the “common” results.
     * GET  /api/nutritionix/foods/common?q=banana
     */
    @GET @Path("common")
    public List<CommonItem> searchCommon(
            @QueryParam("q") @DefaultValue("") String q
    ) {
        try {
            return nutritionixService.searchCommon(q);
        } catch (Exception e) {
            throw new WebApplicationException(
                    "Failed to search common items: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    /**
     * Only the “branded” results.
     * GET  /api/nutritionix/foods/branded?q=banana
     */
    @GET @Path("branded")
    public List<BrandedItem> searchBranded(
            @QueryParam("q") @DefaultValue("") String q
    ) {
        try {
            return nutritionixService.searchBranded(q);
        } catch (Exception e) {
            throw new WebApplicationException(
                    "Failed to search branded items: " + e.getMessage(),
                    Response.Status.BAD_GATEWAY
            );
        }
    }
}
