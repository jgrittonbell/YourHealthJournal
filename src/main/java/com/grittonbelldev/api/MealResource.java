package com.grittonbelldev.api;

import com.grittonbelldev.dto.MealRequestDto;
import com.grittonbelldev.dto.MealResponseDto;
import com.grittonbelldev.service.MealService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * JAX-RS resource exposing RESTful endpoints for Meal operations.
 * <p>
 * Provides CRUD operations for meals: listing, retrieval by ID,
 * creation, update, and deletion. Uses MealService for business logic.
 * </p>
 */
@Path("/meals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MealResource {

    /**
     * Service layer handling meal-related business logic and persistence.
     */
    private final MealService mealService = new MealService();

    /**
     * GET /api/meals
     * <p>
     * Retrieves all meals.
     * </p>
     *
     * @return List of MealResponseDto objects representing all meals
     */
    @GET
    public List<MealResponseDto> listAll() {
        return mealService.listAll();
    }

    /**
     * GET /api/meals/{id}
     * <p>
     * Retrieves a single meal by its identifier.
     * </p>
     *
     * @param id Path parameter representing the meal ID
     * @return MealResponseDto representing the requested meal
     */
    @GET
    @Path("{id}")
    public MealResponseDto getById(@PathParam("id") Long id) {
        return mealService.find(id);
    }

    /**
     * POST /api/meals
     * <p>
     * Creates a new meal based on the provided DTO.
     * Returns 201 Created with Location header.
     * </p>
     *
     * @param dto     DTO containing meal data (name, time, foods)
     * @param uriInfo Context for building the resource URI
     * @return Response with created MealResponseDto and Location header
     */
    @POST
    public Response create(MealRequestDto dto, @Context UriInfo uriInfo) {
        MealResponseDto created = mealService.create(dto);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(created.getId().toString())
                .build();
        return Response.created(uri)
                .entity(created)
                .build();
    }

    /**
     * PUT /api/meals/{id}
     * <p>
     * Updates an existing meal with the given ID.
     * </p>
     *
     * @param id  Path parameter representing the meal ID to update
     * @param dto DTO containing updated meal data
     * @return MealResponseDto representing the updated meal
     */
    @PUT
    @Path("{id}")
    public MealResponseDto update(
            @PathParam("id") Long id,
            MealRequestDto dto
    ) {
        return mealService.update(id, dto);
    }

    /**
     * DELETE /api/meals/{id}
     * <p>
     * Deletes the meal with the given ID.
     * </p>
     *
     * @param id Path parameter representing the meal ID to delete
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        mealService.delete(id);
    }
}