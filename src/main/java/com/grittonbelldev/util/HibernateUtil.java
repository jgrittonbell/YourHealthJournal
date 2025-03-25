package com.grittonbelldev.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class responsible for creating and managing a singleton instance of the Hibernate SessionFactory.
 * This class loads configuration from hibernate.cfg.xml and supports environment-based overrides for database credentials.
 * This solution was based off of a stackoverflow post here: https://stackoverflow.com/questions/8349717/how-can-i-configure-hibernate-with-environment-variable
 * also a link to the documentation for future reference: https://docs.jboss.org/hibernate/orm/3.3/reference/en-US/html/session-configuration.html#configuration-programmatic
 */
public class HibernateUtil {

    // Log4j2 logger for recording Hibernate-related startup and error events
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    // The application's single shared SessionFactory instance
    private static final SessionFactory sessionFactory;

    // Static initializer block to configure and build the SessionFactory when the class is loaded
    static {
        try {
            // Load the default Hibernate configuration from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            // Override database connection properties with environment variables, if set
            // These allow secure deployment on platforms like AWS without storing credentials in code
            configuration.setProperty("hibernate.connection.url", System.getenv("mySQLURL"));
            configuration.setProperty("hibernate.connection.username", System.getenv("mySQLUsername"));
            configuration.setProperty("hibernate.connection.password", System.getenv("mySQLPassword"));

            // Build the SessionFactory using the updated configuration
            sessionFactory = configuration.buildSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully.");

        } catch (Throwable ex) {
            // Log and rethrow fatal errors during Hibernate initialization
            // This prevents the application from running in an uninitialized state
            logger.error("Initial SessionFactory creation failed: {}", ex.getMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Provides access to the singleton SessionFactory instance used throughout the application.
     *
     * @return the configured SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}