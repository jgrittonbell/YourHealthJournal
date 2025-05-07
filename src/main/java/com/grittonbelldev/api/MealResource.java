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
 * REST controller that manages Meal resources scoped to the currently authenticated user.
 *
 * This class defines endpoints that allow users to list, view, create, update,
 * and delete their own meals. All operations are restricted to the user's own data.
 *
 * Endpoints:
 * <ul>
 *     <li>GET    /api/meals           — list all meals for the authenticated user</li>
 *     <li>GET    /api/meals/{id}      — get a single meal by ID if it belongs to the user</li>
 *     <li>POST   /api/meals           — create a new meal for the authenticated user</li>
 *     <li>PUT    /api/meals/{id}      — update an existing meal if it belongs to the user</li>
 *     <li>DELETE /api/meals/{id}      — delete a meal if it belongs to the user</li>
 * </ul>
 */
@Path("/meals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MealResource {

    @Context
    private SecurityContext securityContext;

    // Handles business logic for meal operations
    private final MealService mealService = new MealService();

    /**
     * Helper method that retrieves the authenticated user's ID from the SecurityContext.
     *
     * @return the authenticated user's internal numeric ID
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
     * GET /api/meals
     * Returns all meals created by the authenticated user.
     *
     * @return list of meals in response DTO format
     */
    @GET
    public List<MealResponseDto> listAll() {
        long userId = currentUserId();
        return mealService.listAllForUser(userId);
    }

    /**
     * GET /api/meals/{id}
     * Retrieves a single meal by ID, only if it belongs to the authenticated user.
     *
     * @param id the meal ID
     * @return the meal details in DTO form
     */
    @GET
    @Path("{id}")
    public MealResponseDto getById(@PathParam("id") Long id) {
        long userId = currentUserId();
        return mealService.findForUser(userId, id);
    }

    /**
     * POST /api/meals
     * Creates a new meal associated with the authenticated user.
     *
     * @param dto the meal creation request payload
     * @param uriInfo context to help generate the Location header
     * @return HTTP 201 Created response with Location header and created meal
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
     * Updates a specific meal owned by the authenticated user.
     *
     * @param id the meal ID to update
     * @param dto the updated meal data
     * @return the updated meal in DTO form
     */
    @PUT
    @Path("{id}")
    public MealResponseDto update(
            @PathParam("id") Long id,
            MealRequestDto dto
    ) {
        long userId = currentUserId();
        return mealService.updateForUser(userId, id, dto);
    }

    /**
     * DELETE /api/meals/{id}
     * Deletes a meal if it belongs to the authenticated user.
     *
     * @param id the meal ID to delete
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        long userId = currentUserId();
        mealService.deleteForUser(userId, id);
    }
}
