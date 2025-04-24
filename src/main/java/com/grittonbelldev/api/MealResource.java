package com.grittonbelldev.api;

import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.service.MealService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/meals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MealResource {

    @Inject
    MealService mealService;

    @GET
    public List<MealResponseDto> listAll() {
        return mealService.listAll();
    }

    @GET @Path("{id}")
    public MealResponseDto getById(@PathParam("id") Long id) {
        return mealService.find(id);
    }

    @POST
    public Response create(MealRequestDto dto, @Context UriInfo uriInfo) {
        MealResponseDto created = mealService.create(dto);
        URI uri = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT @Path("{id}")
    public MealResponseDto update(
            @PathParam("id") Long id,
            MealRequestDto dto
    ) {
        return mealService.update(id, dto);
    }

    @DELETE @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        mealService.delete(id);
    }
}
