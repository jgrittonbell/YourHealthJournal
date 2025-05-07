package com.grittonbelldev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for returning OAuth token data from the server to the frontend.
 * This response is sent after exchanging a Cognito authorization code.
 * <p>
 * Example JSON returned to Angular:
 * {
 * "id_token": "eyJ...",
 * "access_token": "eyJ...",
 * "expires_in": 3600
 * }
 */
public class AuthTokenResponse {

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    public AuthTokenResponse() {
    }

    public AuthTokenResponse(String idToken, String accessToken, int expiresIn) {
        this.idToken = idToken;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    /**
     * Gets id token.
     *
     * @return the id token
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * Sets id token.
     *
     * @param idToken the id token
     */
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets expires in.
     *
     * @return the expires in
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets expires in.
     *
     * @param expiresIn the expires in
     */
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
