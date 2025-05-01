package com.grittonbelldev.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.grittonbelldev.auth.CognitoJWTParser;
import com.grittonbelldev.auth.Keys;
import com.grittonbelldev.auth.KeysItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Utility to validate Cognito JWTs and extract claims.
 * <p>
 * Loads region & poolId from cognito.properties via PropertiesLoaderProd.
 * </p>
 */
public class JwtUtils implements PropertiesLoaderProd {
    private static final Logger logger = LogManager.getLogger(JwtUtils.class);

    private static final String REGION;
    private static final String POOL_ID;
    private static final String ISSUER;
    private static final String JWKS_URL;

    // simple in-memory cache of JWKs
    private static Keys cachedKeys;
    private static long lastFetch;
    private static final long CACHE_TTL_MS = 60 * 60 * 1000L; // 1 hour

    static {
        Properties props;
        try {
            props = new PropertiesLoaderProd() {}.loadProperties("/cognito.properties");
        } catch (Exception e) {
            logger.error("Failed to load cognito.properties", e);
            throw new ExceptionInInitializerError(
                    "Unable to load cognito.properties: " + e.getMessage()
            );
        }

        REGION  = props.getProperty("region");
        POOL_ID = props.getProperty("poolId");
        if (REGION == null || POOL_ID == null) {
            throw new ExceptionInInitializerError(
                    "cognito.properties must define region and poolId"
            );
        }

        ISSUER  = "https://cognito-idp." + REGION + ".amazonaws.com/" + POOL_ID;
        JWKS_URL = ISSUER + "/.well-known/jwks.json";
    }

    private JwtUtils() {}

    /** Load (and cache) the JWK set from Cognito */
    private static synchronized Keys fetchJwks() {
        long now = System.currentTimeMillis();
        if (cachedKeys == null || now - lastFetch > CACHE_TTL_MS) {
            Client client = ClientBuilder.newClient();
            cachedKeys = client
                    .target(URI.create(JWKS_URL))
                    .request(MediaType.APPLICATION_JSON)
                    .get(Keys.class);
            lastFetch = now;
        }
        return cachedKeys;
    }

    /**
     * Validate the JWT’s signature, expiration, and issuer,
     * then return its "sub" claim.
     *
     * @param jwt the raw Bearer token
     * @return the Cognito subject ("sub")
     * @throws ProcessingException on any validation failure
     */
    public static String validateAndGetSubject(String jwt) {
        // sanity-check format & header/payload parsing
        CognitoJWTParser.validateJWT(jwt);
        var headerJson = CognitoJWTParser.getHeader(jwt);

        String kid = headerJson.getString("kid");
        String alg = headerJson.getString("alg");
        if (!"RS256".equals(alg)) {
            throw new ProcessingException("Unexpected alg: " + alg);
        }

        // find matching JWK
        List<KeysItem> keys = fetchJwks().getKeys();
        KeysItem match = keys.stream()
                .filter(k -> kid.equals(k.getKid()))
                .findFirst()
                .orElseThrow(() ->
                        new ProcessingException("No JWK found for kid=" + kid)
                );

        try {
            // build RSA public key from n & e
            BigInteger n = new BigInteger(1,
                    Base64.getUrlDecoder().decode(match.getN()));
            BigInteger e = new BigInteger(1,
                    Base64.getUrlDecoder().decode(match.getE()));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pub = kf.generatePublic(new RSAPublicKeySpec(n, e));

            // verify signature
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(pub);
            String[] parts = jwt.split("\\.");
            sig.update((parts[0] + "." + parts[1])
                    .getBytes(StandardCharsets.US_ASCII));
            if (!sig.verify(Base64.getUrlDecoder().decode(parts[2]))) {
                throw new ProcessingException("Invalid JWT signature");
            }

            // parse payload & check exp + iss
            var payload = CognitoJWTParser.getPayload(jwt);
            long expMs = payload.getLong("exp") * 1000L;
            if (System.currentTimeMillis() > expMs) {
                throw new ProcessingException("JWT expired");
            }

            String tokenIssuer = payload.getString("iss");
            if (!ISSUER.equals(tokenIssuer)) {
                throw new ProcessingException("Invalid issuer: " + tokenIssuer);
            }

            // finally, return sub
            return payload.getString("sub");

        } catch (ProcessingException pe) {
            throw pe;
        } catch (Exception ex) {
            throw new ProcessingException("JWT validation error", ex);
        }
    }

    /**
     * Validate the given JWT (signature, exp, issuer) and then
     * return a full DecodedJWT for claim access (email, sub, etc.).
     *
     * @param token the raw Bearer token
     * @return a DecodedJWT with all claims available
     * @throws ProcessingException on any validation failure
     */
    public static DecodedJWT validate(String token) {
        // throws if anything is invalid
        validateAndGetSubject(token);
        // now decode and return
        return JWT.decode(token);
    }
}
