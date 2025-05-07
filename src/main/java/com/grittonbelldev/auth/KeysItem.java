package com.grittonbelldev.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an individual key entry in a JSON Web Key Set (JWKS) returned by AWS Cognito or other identity providers.
 *
 * These keys are used to validate JSON Web Tokens (JWTs) signed by the identity provider.
 * This model supports deserialization of JWKS documents used during token validation.
 */
public class KeysItem {

	// Key type (e.g., RSA)
	@JsonProperty("kty")
	private String kty;

	// Public exponent for the RSA key, encoded using base64url
	@JsonProperty("e")
	private String E;

	// Intended use for the public key (typically "sig" for signature verification)
	@JsonProperty("use")
	private String use;

	// Key ID (kid), used to match the JWT header to the correct key in the set
	@JsonProperty("kid")
	private String kid;

	// Algorithm intended for use with the key (e.g., RS256)
	@JsonProperty("alg")
	private String alg;

	// Modulus for the RSA key, encoded using base64url
	@JsonProperty("n")
	private String N;

	/**
	 * Returns the key type (e.g., RSA).
	 */
	public String getKty() {
		return kty;
	}

	/**
	 * Returns the RSA public exponent (e).
	 */
	public String getE() {
		return E;
	}

	/**
	 * Returns the intended key usage (e.g., "sig").
	 */
	public String getUse() {
		return use;
	}

	/**
	 * Returns the key ID (kid).
	 */
	public String getKid() {
		return kid;
	}

	/**
	 * Returns the cryptographic algorithm used with the key.
	 */
	public String getAlg() {
		return alg;
	}

	/**
	 * Returns the RSA modulus (n).
	 */
	public String getN() {
		return N;
	}
}
