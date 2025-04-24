package com.grittonbelldev.api;

import com.grittonbelldev.dto.GlucoseRequestDto;
import com.grittonbelldev.dto.GlucoseResponseDto;
import com.grittonbelldev.service.GlucoseService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * JAX-RS resource exposing RESTful endpoints for GlucoseReading operations.
 * <p>
 * Provides CRUD operations for glucose readings: listing, retrieval by ID,
 * creation, update, and deletion. Delegates business logic to GlucoseService.
 * </p>
 */
@Path("/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GlucoseResource {

    /**
     * Service layer handling glucose reading persistence and mapping.
     */
    private final GlucoseService glucoseService = new GlucoseService();

    /**
     * GET /api/readings
     * <p>
     * Retrieves all glucose readings.
     * </p>
     *
     * @return List of GlucoseResponseDto representing all readings
     */
    @GET
    public List<GlucoseResponseDto> listAll() {
        return glucoseService.listAll();
    }

    /**
     * GET /api/readings/{id}
     * <p>
     * Retrieves a single glucose reading by its identifier.
     * </p>
     *
     * @param id Path parameter representing the reading ID
     * @return GlucoseResponseDto for the requested reading
     */
    @GET
    @Path("{id}")
    public GlucoseResponseDto getById(@PathParam("id") Long id) {
        return glucoseService.find(id);
    }

    /**
     * POST /api/readings
     * <p>
     * Creates a new glucose reading based on the provided DTO.
     * Returns 201 Created with Location header pointing to the new resource.
     * </p>
     *
     * @param dto     DTO containing measurement data
     * @param uriInfo Context for building the resource URI
     * @return Response with created GlucoseResponseDto and Location header
     */
    @POST
    public Response create(
            GlucoseRequestDto dto,
            @Context UriInfo uriInfo
    ) {
        GlucoseResponseDto created = glucoseService.create(dto);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(created.getId().toString())
                .build();
        return Response.created(uri)
                .entity(created)
                .build();
    }

    /**
     * PUT /api/readings/{id}
     * <p>
     * Updates an existing glucose reading with new values from the DTO.
     * </p>
     *
     * @param id  Path parameter representing the reading ID to update
     * @param dto DTO containing updated measurement data
     * @return GlucoseResponseDto for the updated reading
     */
    @PUT
    @Path("{id}")
    public GlucoseResponseDto update(
            @PathParam("id") Long id,
            GlucoseRequestDto dto
    ) {
        return glucoseService.update(id, dto);
    }

    /**
     * DELETE /api/readings/{id}
     * <p>
     * Deletes the glucose reading with the given ID.
     * </p>
     *
     * @param id Path parameter representing the reading ID to delete
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        glucoseService.delete(id);
    }
}

