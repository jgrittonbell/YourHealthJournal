package com.grittonbelldev.api;

import com.grittonbelldev.dto.GlucoseRequestDto;
import com.grittonbelldev.dto.GlucoseResponseDto;
import com.grittonbelldev.service.GlucoseService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GlucoseResource {

    private final GlucoseService glucoseService = new GlucoseService();

    @GET
    public List<GlucoseResponseDto> listAll() {
        return glucoseService.listAll();
    }

    @GET @Path("{id}")
    public GlucoseResponseDto getById(@PathParam("id") Long id) {
        return glucoseService.find(id);
    }

    @POST
    public Response create(
            GlucoseRequestDto dto,
            @Context UriInfo uriInfo
    ) {
        GlucoseResponseDto created = glucoseService.create(dto);
        URI uri = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT @Path("{id}")
    public GlucoseResponseDto update(
            @PathParam("id") Long id,
            GlucoseRequestDto dto
    ) {
        return glucoseService.update(id, dto);
    }

    @DELETE @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        glucoseService.delete(id);
    }
}
