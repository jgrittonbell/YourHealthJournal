package com.grittonbelldev.api;

import com.grittonbelldev.dto.GlucoseRequestDto;
import com.grittonbelldev.dto.GlucoseResponseDto;
import com.grittonbelldev.service.GlucoseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * JAX-RS resource exposing RESTful endpoints for managing glucose readings.
 *
 * All operations are scoped to the currently authenticated user and enforce
 * user-level access control to ensure that readings cannot be viewed or modified
 * across different users.
 *
 * Endpoints:
 * <ul>
 *     <li>GET    /api/readings         – list all glucose readings for the user</li>
 *     <li>GET    /api/readings/{id}    – retrieve a specific reading</li>
 *     <li>POST   /api/readings         – create a new reading</li>
 *     <li>PUT    /api/readings/{id}    – update an existing reading</li>
 *     <li>DELETE /api/readings/{id}    – delete a reading</li>
 * </ul>
 */
@Path("/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GlucoseResource {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Context
    private SecurityContext securityContext;
    // Service layer responsible for glucose business logic
    private final GlucoseService glucoseService = new GlucoseService();

    /**
     * Helper method to extract the currently authenticated user's ID
     * from the JAX-RS security context.
     *
     * @return the internal user ID
     * @throws WebApplicationException if the user is not authenticated
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
            return userId;
        } catch (NumberFormatException e) {
            logger.error("Invalid principal format: {}", p.getName(), e);
            throw new WebApplicationException("Invalid user principal", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/readings
     * Lists all glucose readings that belong to the current user.
     *
     * @return list of {@link GlucoseResponseDto}
     */
    @GET
    public List<GlucoseResponseDto> listAll() {
        long userId = currentUserId();
        logger.info("GET /api/readings requested by user {}", userId);
        List<GlucoseResponseDto> readings = glucoseService.listAllForUser(userId);
        logger.debug("Found {} readings for user {}", readings.size(), userId);
        return readings;
    }

    /**
     * GET /api/readings/{id}
     * Retrieves a single glucose reading by ID, if it belongs to the current user.
     *
     * @param id the reading ID
     * @return the corresponding {@link GlucoseResponseDto}
     */
    @GET
    @Path("{id}")
    public GlucoseResponseDto getById(@PathParam("id") Long id) {
        long userId = currentUserId();
        logger.info("GET /api/readings/{} requested by user {}", id, userId);
        GlucoseResponseDto reading = glucoseService.findForUser(userId, id);
        logger.debug("Glucose reading {} retrieved for user {}", id, userId);
        return reading;
    }

    /**
     * POST /api/readings
     * Creates a new glucose reading associated with the current user.
     *
     * @param dto the data to create the reading
     * @param uriInfo context for building the URI of the created resource
     * @return a 201 Created response with the created resource
     */
    @POST
    public Response create(
            GlucoseRequestDto dto,
            @Context UriInfo uriInfo
    ) {
        long userId = currentUserId();
        logger.info("POST /api/readings by user {}: {}", userId, dto);
        GlucoseResponseDto created = glucoseService.createForUser(userId, dto);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(created.getId().toString())
                .build();
        logger.debug("Glucose reading created with ID {} for user {}", created.getId(), userId);
        return Response.created(uri)
                .entity(created)
                .build();
    }

    /**
     * PUT /api/readings/{id}
     * Updates an existing glucose reading, if it belongs to the current user.
     *
     * @param id the ID of the reading to update
     * @param dto the updated reading data
     * @return the updated {@link GlucoseResponseDto}
     */
    @PUT
    @Path("{id}")
    public GlucoseResponseDto update(
            @PathParam("id") Long id,
            GlucoseRequestDto dto
    ) {
        long userId = currentUserId();
        logger.info("PUT /api/readings/{} by user {}", id, userId);
        GlucoseResponseDto updated = glucoseService.updateForUser(userId, id, dto);
        logger.debug("Glucose reading {} updated for user {}", id, userId);
        return updated;
    }

    /**
     * DELETE /api/readings/{id}
     * Deletes the specified glucose reading, if it belongs to the current user.
     *
     * @param id the ID of the reading to delete
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        long userId = currentUserId();
        logger.info("DELETE /api/readings/{} by user {}", id, userId);
        glucoseService.deleteForUser(userId, id);
        logger.debug("Glucose reading {} deleted for user {}", id, userId);
    }
}
