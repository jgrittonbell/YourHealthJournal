package com.grittonbelldev.api;

import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.service.MealService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * JAX-RS resource exposing RESTful endpoints for Meal operations,
 * scoped to the currently authenticated user.
 */
@Path("/meals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MealResource {

    @Context
    private SecurityContext securityContext;

    private final MealService mealService = new MealService();

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
     * GET /api/meals
     * Returns only this user’s meals.
     */
    @GET
    public List<MealResponseDto> listAll() {
        long userId = currentUserId();
        return mealService.listAllForUser(userId);
    }

    /**
     * GET /api/meals/{id}
     * Returns the specified meal only if it belongs to this user.
     */
    @GET @Path("{id}")
    public MealResponseDto getById(@PathParam("id") Long id) {
        long userId = currentUserId();
        return mealService.findForUser(userId, id);
    }

    /**
     * POST /api/meals
     * Creates a new meal owned by this user.
     */
    @POST
    public Response create(MealRequestDto dto, @Context UriInfo uriInfo) {
        long userId = currentUserId();
        MealResponseDto created = mealService.createForUser(userId, dto);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(created.getId().toString())
                .build();
        return Response.created(uri)
                .entity(created)
                .build();
    }

    /**
     * PUT /api/meals/{id}
     * Updates an existing meal, only if it belongs to this user.
     */
    @PUT @Path("{id}")
    public MealResponseDto update(
            @PathParam("id") Long id,
            MealRequestDto dto
    ) {
        long userId = currentUserId();
        return mealService.updateForUser(userId, id, dto);
    }

    /**
     * DELETE /api/meals/{id}
     * Deletes the specified meal, only if it belongs to this user.
     */
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        long userId = currentUserId();
        mealService.deleteForUser(userId, id);
    }
}