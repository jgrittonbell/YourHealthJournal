package com.grittonbelldev.controller;

import com.grittonbelldev.persistence.SessionFactoryProvider;
import com.grittonbelldev.util.PropertiesLoaderProd;
import com.grittonbelldev.util.SecretsManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Servlet responsible for initializing application-wide resources at startup.
 *
 * This class:
 * <ul>
 *   <li>Creates the Hibernate SessionFactory for database access</li>
 *   <li>Loads AWS Cognito configuration and secrets for authentication</li>
 *   <li>Loads Nutritionix API credentials for food data integration</li>
 * </ul>
 * All configuration and secrets are stored in the ServletContext for access
 * by other components of the application.
 */
@WebServlet(name = "ApplicationStartup", urlPatterns = {}, loadOnStartup = 1)
public class ApplicationStartup extends HttpServlet implements PropertiesLoaderProd {

    // Logger instance for capturing startup-related events and errors.
    private static final Logger logger = LogManager.getLogger(ApplicationStartup.class);

    /**
     * Lifecycle method triggered when the servlet container starts this servlet.
     * Initializes critical application-level resources including Hibernate, Cognito,
     * and Nutritionix configuration.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        initializeSessionFactory();           // Set up Hibernate ORM layer
        loadAndStoreCognitoProperties();      // Load Cognito secrets and store them in context
        loadAndStoreNutritionixProperties();
        logger.info("Application Startup Complete");// Load Nutritionix API credentials
    }

    /**
     * Initializes the Hibernate SessionFactory.
     * This enables database sessions across the application.
     *
     * @throws ServletException if Hibernate setup fails
     */
    private void initializeSessionFactory() throws ServletException {
        try {
            SessionFactoryProvider.createSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully.");
        } catch (Exception e) {
            logger.error("Failed to initialize SessionFactory: {}", e.getMessage(), e);
            throw new ServletException("Startup failed due to Hibernate error", e);
        }
    }

    /**
     * Loads Cognito credentials and configuration from properties and AWS Secrets Manager.
     * Stores them in the ServletContext so that authentication-related components
     * can access them during user login and token validation flows.
     */
    private void loadAndStoreCognitoProperties() {
        try {
            // Load static configuration from properties file
            Properties props = loadProperties("/cognito.properties");

            // Load secure secrets (client ID and secret) from AWS Secrets Manager
            Map<String, String> secrets = SecretsManagerUtil.getSecretAsMap("yhjSecrets");
            String clientId     = secrets.get("cognitoClientID");
            String clientSecret = secrets.get("cognitoClientSecret");

            // Fail fast if required secrets are missing
            if (clientId == null || clientSecret == null) {
                logger.error("Missing required Cognito credentials");
                throw new RuntimeException("Missing Cognito secrets");
            }

            // Store values into application context for shared access
            ServletContext ctx = getServletContext();
            ctx.setAttribute("client.id", clientId);
            ctx.setAttribute("client.secret", clientSecret);
            ctx.setAttribute("oauthURL",     props.getProperty("oauthURL"));
            ctx.setAttribute("loginURL",     props.getProperty("loginURL"));
            ctx.setAttribute("redirectURL",  props.getProperty("redirectURL"));
            ctx.setAttribute("region",       props.getProperty("region"));
            ctx.setAttribute("poolId",       props.getProperty("poolId"));

            logger.info("Cognito properties loaded successfully.");
        } catch (IOException ioe) {
            logger.error("Error loading Cognito properties: {}", ioe.getMessage(), ioe);
        } catch (Exception e) {
            logger.error("Error loading Cognito properties: {}", e.getMessage(), e);
        }
    }

    /**
     * Loads Nutritionix API credentials from AWS Secrets Manager and stores them
     * in the application context. These credentials are used for authenticated calls
     * to the Nutritionix natural language and instant search endpoints.
     */
    private void loadAndStoreNutritionixProperties() {
        Map<String, String> secrets = SecretsManagerUtil.getSecretAsMap("yhjSecrets");
        String appId  = secrets.get("nutritionixID");
        String appKey = secrets.get("nutritionixKey");

        // Log and return early if API keys are not available
        if (appId == null || appKey == null) {
            logger.error("Missing Nutritionix API credentials; features depending on Nutritionix will be unavailable.");
            return;
        }

        // Store API credentials in the application context
        ServletContext ctx = getServletContext();
        ctx.setAttribute("nutritionix.appId",  appId);
        ctx.setAttribute("nutritionix.appKey", appKey);
        logger.info("Nutritionix API credentials loaded successfully.");
    }

    /**
     * Lifecycle method triggered when the servlet container is shutting down.
     * Ensures Hibernate resources are released properly.
     */
    @Override
    public void destroy() {
        try {
            if (SessionFactoryProvider.getSessionFactory() != null) {
                SessionFactoryProvider.getSessionFactory().close();
                logger.info("Hibernate SessionFactory closed.");
            }
        } catch (Exception e) {
            logger.error("Error shutting down SessionFactory: {}", e.getMessage(), e);
        }
    }
}
