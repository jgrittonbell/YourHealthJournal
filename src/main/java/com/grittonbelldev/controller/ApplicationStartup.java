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
 * Servlet that initializes application-wide resources on startup.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Initialize Hibernate SessionFactory</li>
 *   <li>Load and store AWS Cognito configuration</li>
 *   <li>Load and store Nutritionix API credentials</li>
 * </ul>
 * Configuration values are placed into the ServletContext for use
 * by other components (e.g., JAX-RS resources).
 * </p>
 */
@WebServlet(name = "ApplicationStartup", urlPatterns = {}, loadOnStartup = 1)
public class ApplicationStartup extends HttpServlet implements PropertiesLoaderProd {

    private static final Logger logger = LogManager.getLogger(ApplicationStartup.class);

    /**
     * Called once when the application starts.
     * Initializes all necessary resources.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        initializeSessionFactory();
        loadAndStoreCognitoProperties();
        loadAndStoreNutritionixProperties();
    }

    /**
     * Initializes Hibernate's SessionFactory via the SessionFactoryProvider.
     *
     * @throws ServletException if initialization fails
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
     * Loads AWS Cognito settings from properties file and Secrets Manager,
     * and stores them in the ServletContext.
     */
    private void loadAndStoreCognitoProperties() {
        try {
            Properties props = loadProperties("/cognito.properties");
            Map<String, String> secrets = SecretsManagerUtil.getSecretAsMap("yhjSecrets");

            String clientId     = secrets.get("cognitoClientID");
            String clientSecret = secrets.get("cognitoClientSecret");

            if (clientId == null || clientSecret == null) {
                logger.error("Missing required Cognito credentials");
                throw new RuntimeException("Missing Cognito secrets");
            }

            ServletContext ctx = getServletContext();
            ctx.setAttribute("client.id", clientId);
            ctx.setAttribute("client.secret", clientSecret);
            ctx.setAttribute("oauthURL",   props.getProperty("oauthURL"));
            ctx.setAttribute("loginURL",   props.getProperty("loginURL"));
            ctx.setAttribute("redirectURL", props.getProperty("redirectURL"));
            ctx.setAttribute("region",      props.getProperty("region"));
            ctx.setAttribute("poolId",      props.getProperty("poolId"));

            logger.info("Cognito properties loaded successfully.");
        } catch (IOException ioe) {
            logger.error("Error loading Cognito properties: {}", ioe.getMessage(), ioe);
        } catch (Exception e) {
            logger.error("Error loading Cognito properties: {}", e.getMessage(), e);
        }
    }

    /**
     * Loads Nutritionix API credentials from AWS Secrets Manager or environment,
     * and stores them in the ServletContext.
     */
    private void loadAndStoreNutritionixProperties() {
        Map<String, String> secrets = SecretsManagerUtil.getSecretAsMap("yhjSecrets");
        String appId  = secrets.get("nutritionixID");
        String appKey = secrets.get("nutritionixKey");

        if (appId == null || appKey == null) {
            logger.error("Missing Nutritionix API credentials; features depending on Nutritionix will be unavailable.");
            return;
        }

        ServletContext ctx = getServletContext();
        ctx.setAttribute("nutritionix.appId",  appId);
        ctx.setAttribute("nutritionix.appKey", appKey);
        logger.info("Nutritionix API credentials loaded successfully.");
    }

    /**
     * Called once when the application shuts down.
     * Closes the Hibernate SessionFactory to release resources.
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
