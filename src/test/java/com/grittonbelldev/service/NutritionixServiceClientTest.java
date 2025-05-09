package com.grittonbelldev.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
@Disabled
public class NutritionixServiceClientTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    // This uses environment variables and will work in the local environment only
    private static final String APP_ID  = System.getenv("nutritionixID");
    private static final String APP_KEY = System.getenv("nutritionixKey");

    @Disabled
    @Test
    public void testInstantSearchEndpoint() {
        assertNotNull("nutritionixID must be set",  APP_ID);
        assertNotNull("nutritionixKey must be set", APP_KEY);

        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("https://trackapi.nutritionix.com/v2/search/instant")
                .queryParam("query", "apple");

        Response resp = target.request(MediaType.APPLICATION_JSON)
                .header("x-app-id",  APP_ID)
                .header("x-app-key", APP_KEY)
                .get();

        // 1) Check we got a 200 OK
        assertEquals(200, resp.getStatus());

        // 2) Check the body looks like an instant-search result
        String json = resp.readEntity(String.class);
        assertTrue("Response should contain a 'common' array", json.contains("\"common\""));
        logger.info(json);

        resp.close();
    }
}

