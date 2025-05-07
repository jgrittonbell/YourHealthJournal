package com.grittonbelldev.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a JSON Web Key Set (JWKS) document that contains a list of public keys.
 *
 * This model is used to deserialize the full JWKS response from AWS Cognito or other
 * OpenID Connect-compliant identity providers. Each key in the list can be used to
 * verify a JWT's signature, depending on the "kid" (Key ID) field in the token header.
 */
public class Keys {

	// A list of individual public keys used for verifying JWTs
	@JsonProperty("keys")
	private List<KeysItem> keys;

	/**
	 * Returns the list of public keys contained in the JWKS document.
	 */
	public List<KeysItem> getKeys() {
		return keys;
	}
}
