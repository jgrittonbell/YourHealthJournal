package com.grittonbelldev.controller;

import com.grittonbelldev.util.HibernateUtil;
import com.grittonbelldev.util.SecretsManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Map;

/**
 * This servlet is automatically loaded when the web application starts.
 * It initializes the Hibernate SessionFactory so that the application is ready to interact with the database.
 * This class also ensures that resources are released properly when the application shuts down.
 */
@WebServlet(name = "ApplicationStartup", urlPatterns = {}, loadOnStartup = 1)
public class ApplicationStartup extends HttpServlet {

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
        try {
            // Force Hibernate initialization
            HibernateUtil.getSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully.");

            // Load Cognito secrets
            //TODO ADD Cognito stuff
            Map<String, String> secrets = SecretsManagerUtil.getSecretAsMap("yhjSecrets");
            String clientId = secrets.get("COGNITO_CLIENT_ID");
            String clientSecret = secrets.get("COGNITO_CLIENT_SECRET");

            logger.info("Cognito Client ID: {}", clientId != null ? clientId : "not set");
            logger.info("Cognito Client Secret: {}", clientSecret != null ? "[set]" : "not set");

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
