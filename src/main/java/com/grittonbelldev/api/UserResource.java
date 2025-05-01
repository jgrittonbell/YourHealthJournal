package com.grittonbelldev.api;

import com.grittonbelldev.dto.UserDto;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;

/**
 * JAX-RS resource for the currently authenticated user’s profile.
 *
 * Endpoints:
 *   GET  /api/users/me      → fetch your profile
 *   PUT  /api/users/me      → update your profile
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Context
    private SecurityContext securityContext;

    private final UserService userService = new UserService();

    /**
     * GET /api/users/me
     * @return the profile of the authenticated user
     */
    @GET
    @Path("me")
    public UserDto getMyProfile() {
        long userId = getCurrentUserId();
        User user = userService.getById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
        }
        return toDto(user);
    }

    /**
     * PUT /api/users/me
     * Updates first name, last name, and/or email for the authenticated user.
     *
     * @param dto contains the fields to update (first_name, last_name, email)
     * @return the updated profile
     */
    @PUT
    @Path("me")
    public UserDto updateMyProfile(UserDto dto) {
        long userId = getCurrentUserId();
        User updated = userService.updateProfile(
                userId,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );
        return toDto(updated);
    }

    /** Helper: extract the internal user ID from SecurityContext */
    private long getCurrentUserId() {
        Principal p = securityContext.getUserPrincipal();
        if (p == null) {
            throw new WebApplicationException("Not authenticated", Response.Status.UNAUTHORIZED);
        }
        try {
            return Long.parseLong(p.getName());
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Invalid principal", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /** Map your User entity to the public UserDto */
    private static UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        dto.setEmail(u.getEmail());
        return dto;
    }
}
