package com.grittonbelldev.api;

import com.grittonbelldev.dto.GlucoseRequestDto;
import com.grittonbelldev.dto.GlucoseResponseDto;
import com.grittonbelldev.service.GlucoseService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * JAX-RS resource exposing RESTful endpoints for GlucoseReading operations,
 * scoped to the currently authenticated user.
 */
@Path("/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GlucoseResource {

    @Context
    private SecurityContext securityContext;

    private final GlucoseService glucoseService = new GlucoseService();

    /**
     * Extracts the internal user ID from the SecurityContext.
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
     * GET /api/readings
     * Returns only this user’s glucose readings.
     */
    @GET
    public List<GlucoseResponseDto> listAll() {
        long userId = currentUserId();
        return glucoseService.listAllForUser(userId);
    }

    /**
     * GET /api/readings/{id}
     * Returns the specified reading only if it belongs to this user.
     */
    @GET
    @Path("{id}")
    public GlucoseResponseDto getById(@PathParam("id") Long id) {
        long userId = currentUserId();
        return glucoseService.findForUser(userId, id);
    }

    /**
     * POST /api/readings
     * Creates a new glucose reading owned by this user.
     */
    @POST
    public Response create(
            GlucoseRequestDto dto,
            @Context UriInfo uriInfo
    ) {
        long userId = currentUserId();
        GlucoseResponseDto created = glucoseService.createForUser(userId, dto);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(created.getId().toString())
                .build();
        return Response.created(uri)
                .entity(created)
                .build();
    }

    /**
     * PUT /api/readings/{id}
     * Updates an existing reading, only if it belongs to this user.
     */
    @PUT
    @Path("{id}")
    public GlucoseResponseDto update(
            @PathParam("id") Long id,
            GlucoseRequestDto dto
    ) {
        long userId = currentUserId();
        return glucoseService.updateForUser(userId, id, dto);
    }

    /**
     * DELETE /api/readings/{id}
     * Deletes the specified reading, only if it belongs to this user.
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        long userId = currentUserId();
        glucoseService.deleteForUser(userId, id);
    }
}
