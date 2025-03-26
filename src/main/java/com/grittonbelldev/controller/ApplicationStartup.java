package com.grittonbelldev.controller;

import com.grittonbelldev.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

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
            // Force Hibernate to initialize by accessing the SessionFactory
            HibernateUtil.getSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully.");

            // Log the DB connection info (excluding sensitive data like the password)
            String dbUrl = System.getenv("mySQLURL");
            String dbUser = System.getenv("mySQLUsername");

            // Only log the values if environment variables have been set (not using the default placeholders)
            logger.info("DB URL: {}", !"url".equals(dbUrl) ? dbUrl : "not set");
            logger.info("DB User: {}", !"username".equals(dbUser) ? dbUser : "not set");

        } catch (Exception e) {
            // Log and rethrow if initialization fails to stop the application from continuing in an unstable state
            logger.error("Error initializing Hibernate: {}", e.getMessage(), e);
            throw new ServletException("Hibernate initialization failed", e);
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
