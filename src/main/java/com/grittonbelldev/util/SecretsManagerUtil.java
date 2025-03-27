package com.grittonbelldev.util;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to retrieve secrets from AWS Secrets Manager.
 * Automatically falls back to system environment variables if AWS retrieval fails.
 */
public class SecretsManagerUtil {

    // Log4j2 logger for consistent structured logging
    private static final Logger logger = LogManager.getLogger(SecretsManagerUtil.class);

    // The AWS region your secret is stored in (e.g. us-east-2)
    private static final Region REGION = Region.US_EAST_2;

    // Jackson object mapper to convert secret JSON string into a Java Map
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Retrieves a secret from AWS Secrets Manager and returns it as a key-value map.
     * Falls back to loading from system environment variables if AWS call fails.
     *
     * @param secretName the name of the AWS Secrets Manager secret
     * @return a Map of secret keys and values (or environment fallback)
     */
    public static Map<String, String> getSecretAsMap(String secretName) {
        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(REGION)
                .build()) {

            logger.info("Attempting to fetch secret '{}' from AWS Secrets Manager in region {}", secretName, REGION);

            // Build request to fetch secret string
            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            // Call AWS Secrets Manager
            GetSecretValueResponse response = client.getSecretValue(request);

            if (response.secretString() != null) {
                logger.info("Successfully retrieved secret '{}'", secretName);
                return MAPPER.readValue(response.secretString(), Map.class);
            } else {
                logger.warn("Secret '{}' returned null content", secretName);
            }

        } catch (SecretsManagerException e) {
            logger.error("SecretsManager exception: {}", e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error retrieving or parsing secret '{}': {}", secretName, e.getMessage(), e);
        }

        // Fallback to environment variables
        logger.warn("Falling back to environment variables for secret '{}'", secretName);

        Map<String, String> fallback = new HashMap<>();
        fallback.put("mySQLURL", System.getenv("mySQLURL"));
        fallback.put("mySQLUsername", System.getenv("mySQLUsername"));
        fallback.put("mySQLPassword", System.getenv("mySQLPassword"));
        fallback.put("cognitoClientID", System.getenv("COGNITO_CLIENT_ID"));
        fallback.put("cognitoClientSecret", System.getenv("COGNITO_CLIENT_SECRET"));
        return fallback;
    }
}
