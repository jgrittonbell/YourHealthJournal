package com.grittonbelldev.api;

import com.grittonbelldev.service.FavoriteService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/favorites")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class FavoriteResource {

    private FavoriteService favoriteService = new FavoriteService();

    @POST @Path("meals/{mealId}")
    public Response favoriteMeal(@PathParam("mealId") Long mealId) {
        favoriteService.favoriteMeal(mealId);
        return Response.ok().build();
    }

    @DELETE @Path("meals/{mealId}")
    public Response unfavoriteMeal(@PathParam("mealId") Long mealId) {
        favoriteService.unfavoriteMeal(mealId);
        return Response.noContent().build();
    }

    @POST @Path("foods/{foodId}")
    public Response favoriteFood(@PathParam("foodId") Long foodId) {
        favoriteService.favoriteFood(foodId);
        return Response.ok().build();
    }

    @DELETE @Path("foods/{foodId}")
    public Response unfavoriteFood(@PathParam("foodId") Long foodId) {
        favoriteService.unfavoriteFood(foodId);
        return Response.noContent().build();
    }
}
