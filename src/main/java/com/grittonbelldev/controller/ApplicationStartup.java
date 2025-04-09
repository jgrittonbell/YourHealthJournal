package com.grittonbelldev.controller;

import com.grittonbelldev.util.HibernateUtil;
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
 * This servlet is automatically loaded when the web application starts.
 * It initializes the Hibernate SessionFactory so that the application is ready to interact with the database.
 * This class also ensures that resources are released properly when the application shuts down.
 */
@WebServlet(name = "ApplicationStartup", urlPatterns = {}, loadOnStartup = 1)
public class ApplicationStartup extends HttpServlet implements PropertiesLoaderProd {

    // Log4j2 logger for logging application startup/shutdown events
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Called once when the application starts.
     * This method initializes the Hibernate SessionFactory and logs the status of the database connection.
     *
     * @throws ServletException if Hibernate initialization fails
     */
    @Override
    public void init() throws ServletException {
        super.init();
        initializeHibernateSessionFactory();
        loadAndStoreCognitoProperties();
    }

    /**
     * Loads properties from cognito.properties and stores them in the ServletContext.
     */
    private void loadAndStoreCognitoProperties() {
        try {
            Properties properties = loadProperties("/cognito.properties");

            // Load Cognito secrets
            Map<String, String> secrets = SecretsManagerUtil.getSecretAsMap("yhjSecrets");
            String clientId = secrets.get("cognitoClientID");
            String clientSecret = secrets.get("cognitoClientSecret");

            if (clientId == null || clientSecret == null) {
                logger.error("Missing required Cognito secrets. Cannot continue startup.");
                throw new RuntimeException("Missing Cognito secrets");
            }

            logger.info("Cognito Client ID: {}", clientId);
            logger.info("Cognito Client Secret: [set]");

            ServletContext context = getServletContext();
            context.setAttribute("client.id", clientId);
            context.setAttribute("client.secret", clientSecret);
            context.setAttribute("oauthURL", properties.getProperty("oauthURL"));
            context.setAttribute("loginURL", properties.getProperty("loginURL"));
            context.setAttribute("redirectURL", properties.getProperty("redirectURL"));
            context.setAttribute("region", properties.getProperty("region"));
            context.setAttribute("poolId", properties.getProperty("poolId"));

            logger.info("Application Startup: Cognito properties loaded.");
        } catch (IOException ioException) {
            logger.error("IOError loading properties at startup: {}", ioException.getMessage());
        } catch (Exception exception) {
            logger.error("Error loading properties at startup: {}", exception.getMessage());
        }
    }

    private void initializeHibernateSessionFactory() throws ServletException {
        try {
            // Force Hibernate initialization
            HibernateUtil.getSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully.");

        } catch (Exception e) {
            logger.error("Error initializing application: {}", e.getMessage(), e);
            throw new ServletException("Startup failed", e);
        }
    }

    /**
     * Called once when the application shuts down.
     * This method cleanly closes the Hibernate SessionFactory to release all resources.
     */
    @Override
    public void destroy() {
        try {
            HibernateUtil.getSessionFactory().close();
            logger.info("Hibernate SessionFactory closed.");
        } catch (Exception e) {
            logger.error("Error shutting down Hibernate: {}", e.getMessage(), e);
        }
    }
}
