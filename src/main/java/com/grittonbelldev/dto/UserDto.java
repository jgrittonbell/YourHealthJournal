package com.grittonbelldev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data-transfer object for exposing user profile information via the API.
 *
 * <p>
 * This DTO is used to send user data (ID, name, email) to frontend clients.
 * Jackson annotations are used to control JSON serialization, including custom field names and omitting null values.
 * </p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    /** Internal unique identifier for the user. */
    private Long id;

    /** First name of the user. */
    private String firstName;

    /** Last name of the user. */
    private String lastName;

    /** Email address of the user. */
    private String email;

    /** No-argument constructor for serialization frameworks. */
    public UserDto() {}

    /**
     * All-argument constructor for quickly building a DTO.
     *
     * @param id        the user's unique identifier
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param email     the user's email address
     */
    public UserDto(Long id, String firstName, String lastName, String email) {
        this.id        = id;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
    }

    /**
     * Get the user ID.
     *
     * @return the user ID
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * Set the user ID.
     *
     * @param id the new ID value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user's first name.
     *
     * @return first name
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the user's first name.
     *
     * @param firstName the new first name value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the user's last name.
     *
     * @return last name
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the user's last name.
     *
     * @param lastName the new last name value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the user's email address.
     *
     * @return email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * Set the user's email address.
     *
     * @param email the new email value
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
