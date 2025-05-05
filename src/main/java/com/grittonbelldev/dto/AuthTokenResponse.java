package com.grittonbelldev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for returning OAuth token data from the server to the frontend.
 * This response is sent after exchanging a Cognito authorization code.
 *
 * Example JSON returned to Angular:
 * {
 *   "id_token": "eyJ...",
 *   "access_token": "eyJ...",
 *   "expires_in": 3600
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

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
