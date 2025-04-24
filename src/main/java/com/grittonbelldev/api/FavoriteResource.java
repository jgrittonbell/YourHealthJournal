package com.grittonbelldev.api;

import com.grittonbelldev.service.FavoriteService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS resource exposing endpoints for managing user favorites.
 * <p>
 * Allows clients to mark meals or foods as favorites and remove those markings.
 * Delegates business logic to FavoriteService.
 * </p>
 */
@Path("/favorites")
@Produces(MediaType.APPLICATION_JSON)
public class FavoriteResource {

    /**
     * Service layer handling favorite/unfavorite operations.
     */
    private final FavoriteService favoriteService = new FavoriteService();

    /**
     * POST /api/favorites/meals/{mealId}
     * <p>
     * Marks the meal identified by mealId as a favorite for the current user.
     * </p>
     *
     * @param mealId the ID of the meal to favorite
     * @return HTTP 200 OK if the operation succeeds
     */
    @POST
    @Path("meals/{mealId}")
    public Response favoriteMeal(@PathParam("mealId") Long mealId) {
        favoriteService.favoriteMeal(mealId);
        return Response.ok().build();
    }

    /**
     * DELETE /api/favorites/meals/{mealId}
     * <p>
     * Removes the favorite marking from the specified meal.
     * </p>
     *
     * @param mealId the ID of the meal to unfavorite
     * @return HTTP 204 No Content if deletion succeeds
     */
    @DELETE
    @Path("meals/{mealId}")
    public Response unfavoriteMeal(@PathParam("mealId") Long mealId) {
        favoriteService.unfavoriteMeal(mealId);
        return Response.noContent().build();
    }

    /**
     * POST /api/favorites/foods/{foodId}
     * <p>
     * Marks the food identified by foodId as a favorite for the current user.
     * </p>
     *
     * @param foodId the ID of the food to favorite
     * @return HTTP 200 OK if the operation succeeds
     */
    @POST
    @Path("foods/{foodId}")
    public Response favoriteFood(@PathParam("foodId") Long foodId) {
        favoriteService.favoriteFood(foodId);
        return Response.ok().build();
    }

    /**
     * DELETE /api/favorites/foods/{foodId}
     * <p>
     * Removes the favorite marking from the specified food.
     * </p>
     *
     * @param foodId the ID of the food to unfavorite
     * @return HTTP 204 No Content if deletion succeeds
     */
    @DELETE
    @Path("foods/{foodId}")
    public Response unfavoriteFood(@PathParam("foodId") Long foodId) {
        favoriteService.unfavoriteFood(foodId);
        return Response.noContent().build();
    }
}
