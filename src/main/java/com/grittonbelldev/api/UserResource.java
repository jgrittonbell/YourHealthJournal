package com.grittonbelldev.api;

import com.grittonbelldev.dto.UserDto;
import com.grittonbelldev.entity.User;
import com.grittonbelldev.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;

/**
 * RESTful resource that provides profile-related operations for the
 * currently authenticated user. This class exposes two endpoints
 * for retrieving and updating user profile data.
 *
 * Endpoints:
 * <ul>
 *   <li>GET  /api/users/me → fetch the authenticated user's profile</li>
 *   <li>PUT  /api/users/me → update the authenticated user's profile</li>
 * </ul>
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    // Injected security context used to determine the identity of the authenticated user
    @Context
    private SecurityContext securityContext;

    // Service class responsible for interacting with the User entity
    private final UserService userService = new UserService();

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @return a DTO representing the user's profile
     * @throws WebApplicationException if the user is not found
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
     * Updates the first name, last name, and/or email address for the
     * currently authenticated user.
     *
     * @param dto the fields to update
     * @return the updated user profile as a DTO
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

    /**
     * Extracts the authenticated user's ID from the SecurityContext.
     * This ID was placed into the context by JwtAuthFilter.
     *
     * @return the internal user ID
     * @throws WebApplicationException if the user is not authenticated or the ID is invalid
     */
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

    /**
     * Maps a User entity to its corresponding UserDto.
     * This ensures only public profile fields are exposed to the client.
     *
     * @param u the User entity
     * @return the corresponding UserDto
     */
    private static UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        dto.setEmail(u.getEmail());
        return dto;
    }
}
