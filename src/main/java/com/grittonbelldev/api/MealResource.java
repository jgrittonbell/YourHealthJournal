package com.grittonbelldev.api;

import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.service.MealService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private final Logger logger = LogManager.getLogger(this.getClass());

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
            logger.error("User is not authenticated: SecurityContext.getUserPrincipal() returned null.");
            throw new WebApplicationException("Not authenticated", Response.Status.UNAUTHORIZED);
        }
        try {
            long userId = Long.parseLong(p.getName());
            logger.debug("Authenticated user ID: {}", userId);
            return Long.parseLong(p.getName());
        } catch (NumberFormatException e) {
            logger.error("Invalid principal format: {}", p.getName(), e);
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
        logger.info("GET /api/meals requested by user {}", userId);
        List<MealResponseDto> meals = mealService.listAllForUser(userId);
        logger.debug("Found {} meals for user {}", meals.size(), userId);
        return meals;
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
        logger.info("GET /api/meals/{} requested by user {}", id, userId);
        MealResponseDto meal = mealService.findForUser(userId, id);
        logger.debug("Meal {} retrieved for user {}", id, userId);
        return meal;
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
        logger.info("POST /api/meals by user {}: {}", userId, dto);
        MealResponseDto created = mealService.createForUser(userId, dto);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(created.getId().toString())
                .build();
        logger.debug("Meal created with ID {} for user {}", created.getId(), userId);
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
        logger.info("PUT /api/meals/{} by user {}", id, userId);
        MealResponseDto updated = mealService.updateForUser(userId, id, dto);
        logger.debug("Meal {} updated for user {}", id, userId);
        return updated;
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
        logger.info("DELETE /api/meals/{} by user {}", id, userId);
        mealService.deleteForUser(userId, id);
        logger.debug("Meal {} deleted for user {}", id, userId);
    }
}
