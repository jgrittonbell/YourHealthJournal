package com.grittonbelldev.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for exchanging an OAuth 2.0 authorization code with Cognito.
 * This class represents the JSON body received from the Angular frontend.
 *
 * Example JSON:
 * {
 *   "code": "abc123"
 * }
 */
public class AuthCodeRequest {

    private final String code;

    /**
     * Constructs an AuthCodeRequest from JSON using the provided code.
     *
     * @param code the authorization code received from Cognito
     */
    @JsonCreator
    public AuthCodeRequest(@JsonProperty("code") String code) {
        this.code = code;
    }

    /**
     * Gets the authorization code.
     *
     * @return the code string
     */
    public String getCode() {
        return code;
    }
}