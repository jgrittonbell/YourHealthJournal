package com.grittonbelldev.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simple representation of the JWT header returned by AWS Cognito tokens.
 *
 * This class maps only the fields necessary for validating the token's
 * signature using the JWKS (JSON Web Key Set) endpoint. These fields
 * help determine which public key to use when verifying the JWT.
 *
 * Fields:
 * <ul>
 *   <li><strong>kid</strong>: Key ID, used to match the correct public key from JWKS.</li>
 *   <li><strong>alg</strong>: Algorithm used to sign the token (e.g., RS256).</li>
 * </ul>
 */
public class CognitoTokenHeader {

	@JsonProperty("kid")
	private String kid;  // Key ID used to select the correct key for JWT verification

	@JsonProperty("alg")
	private String alg;  // The algorithm used to sign the token

	/**
	 * Returns the Key ID used to identify the public key in JWKS.
	 *
	 * @return the key ID
	 */
	public String getKid() {
		return kid;
	}

	/**
	 * Returns the signing algorithm of the JWT.
	 *
	 * @return the algorithm name
	 */
	public String getAlg() {
		return alg;
	}
}
