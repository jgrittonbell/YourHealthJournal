package com.grittonbelldev.api;

import com.grittonbelldev.dto.FoodResponseDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.service.FavoriteService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.List;

/**
 * JAX-RS resource exposing endpoints for managing user favorites,
 * scoped to the currently authenticated user.
 */
@Path("/favorites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoriteResource {

    @Context
    private SecurityContext securityContext;

    private final FavoriteService favoriteService = new FavoriteService();

    /** Extract the internal user ID from the SecurityContext. */
    private long currentUserId() {
        Principal p = securityContext.getUserPrincipal();
        if (p == null) {
            throw new WebApplicationException("Not authenticated", Response.Status.UNAUTHORIZED);
        }
        try {
            return Long.parseLong(p.getName());
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Invalid user principal", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST /api/favorites/meals/{mealId}
     * Marks the meal as favorite for the current user.
     */
    @POST
    @Path("meals/{mealId}")
    public Response favoriteMeal(@PathParam("mealId") Long mealId) {
        long userId = currentUserId();
        favoriteService.favoriteMealForUser(userId, mealId);
        return Response.ok().build();
    }

    /**
     * DELETE /api/favorites/meals/{mealId}
     * Removes the meal from the current user’s favorites.
     */
    @DELETE
    @Path("meals/{mealId}")
    public Response unfavoriteMeal(@PathParam("mealId") Long mealId) {
        long userId = currentUserId();
        favoriteService.unfavoriteMealForUser(userId, mealId);
        return Response.noContent().build();
    }

    /**
     * POST /api/favorites/foods/{foodId}
     * Marks the food as favorite for the current user.
     */
    @POST
    @Path("foods/{foodId}")
    public Response favoriteFood(@PathParam("foodId") Long foodId) {
        long userId = currentUserId();
        favoriteService.favoriteFoodForUser(userId, foodId);
        return Response.ok().build();
    }

    /**
     * DELETE /api/favorites/foods/{foodId}
     * Removes the food from the current user’s favorites.
     */
    @DELETE
    @Path("foods/{foodId}")
    public Response unfavoriteFood(@PathParam("foodId") Long foodId) {
        long userId = currentUserId();
        favoriteService.unfavoriteFoodForUser(userId, foodId);
        return Response.noContent().build();
    }

    /**
     * GET /api/favorites/meals
     * <p>Return all meals this user has favorited.</p>
     */
    @GET @Path("meals")
    public List<MealResponseDto> listFavoriteMeals() {
        return favoriteService.listFavoriteMeals(currentUserId());
    }

    /**
     * GET /api/favorites/foods
     * <p>Return all foods this user has favorited.</p>
     */
    @GET @Path("foods")
    public List<FoodResponseDto> listFavoriteFoods() {
        return favoriteService.listFavoriteFoods(currentUserId());
    }
}
