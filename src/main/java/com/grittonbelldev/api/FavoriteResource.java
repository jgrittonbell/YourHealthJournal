package com.grittonbelldev.api;

import com.grittonbelldev.dto.FoodResponseDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.service.FavoriteService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.List;

/**
 * JAX-RS resource exposing endpoints for managing user favorite meals and foods.
 *
 * All operations are restricted to the authenticated user. This class allows
 * users to mark meals and foods as favorites, remove those favorites, and fetch
 * lists of favorited meals and foods.
 *
 * Endpoints:
 * <ul>
 *   <li>POST   /api/favorites/meals/{mealId} – mark a meal as favorite</li>
 *   <li>DELETE /api/favorites/meals/{mealId} – remove a meal from favorites</li>
 *   <li>POST   /api/favorites/foods/{foodId} – mark a food as favorite</li>
 *   <li>DELETE /api/favorites/foods/{foodId} – remove a food from favorites</li>
 *   <li>GET    /api/favorites/meals          – list favorited meals</li>
 *   <li>GET    /api/favorites/foods          – list favorited foods</li>
 * </ul>
 */
@Path("/favorites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoriteResource {

    @Context
    private SecurityContext securityContext;

    // Service layer that contains the business logic for managing favorites
    private final FavoriteService favoriteService = new FavoriteService();

    /**
     * Helper method to extract the currently authenticated user's ID
     * from the JAX-RS security context.
     *
     * @return the internal user ID
     * @throws WebApplicationException if authentication is missing or invalid
     */
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
     * Marks a meal as favorite for the authenticated user.
     *
     * @param mealId the ID of the meal to mark as favorite
     * @return 200 OK if successful
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
     * Removes a meal from the user's favorites.
     *
     * @param mealId the ID of the meal to unfavorite
     * @return 204 No Content if successful
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
     * Marks a food item as favorite for the authenticated user.
     *
     * @param foodId the ID of the food to mark as favorite
     * @return 200 OK if successful
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
     * Removes a food item from the user's favorites.
     *
     * @param foodId the ID of the food to unfavorite
     * @return 204 No Content if successful
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
     * Retrieves all meals that the authenticated user has favorited.
     *
     * @return a list of favorited meals
     */
    @GET
    @Path("meals")
    public List<MealResponseDto> listFavoriteMeals() {
        return favoriteService.listFavoriteMeals(currentUserId());
    }

    /**
     * GET /api/favorites/foods
     * Retrieves all foods that the authenticated user has favorited.
     *
     * @return a list of favorited foods
     */
    @GET
    @Path("foods")
    public List<FoodResponseDto> listFavoriteFoods() {
        return favoriteService.listFavoriteFoods(currentUserId());
    }
}
