package com.grittonbelldev.util;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;

public class SecretsManagerUtil {

    private static final Region REGION = Region.US_EAST_2; // Change if needed
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Map<String, String> getSecretAsMap(String secretName) {
        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(REGION)
                .build()) {

            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse response = client.getSecretValue(request);

            if (response.secretString() != null) {
                return MAPPER.readValue(response.secretString(), Map.class);
            } else {
                System.err.println("Secret string is null");
                return Collections.emptyMap();
            }

        } catch (SecretsManagerException e) {
            System.err.println("AWS Secrets Manager error: " + e.awsErrorDetails().errorMessage());
            return Collections.emptyMap();
        } catch (Exception e) {
            System.err.println("Failed to fetch or parse secret: " + e.getMessage());
            return Collections.emptyMap();
        }
    }
}

