package com.grittonbelldev.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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

    // This holds the configuration registry that manages Hibernate services and settings
    // including database connections, dialects, mapping, etc.
    private static StandardServiceRegistry registry;

    // Static initializer block to configure and build the SessionFactory when the class is loaded
    static {
        try {
            // Create registry with configuration
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure();

            // Override connection properties from environment variables
            String dbUrl = System.getenv("mySQLURL");
            String dbUser = System.getenv("mySQLUsername");
            String dbPass = System.getenv("mySQLPassword");

            if (dbUrl != null) builder.applySetting("hibernate.connection.url", dbUrl);
            if (dbUser != null) builder.applySetting("hibernate.connection.username", dbUser);
            if (dbPass != null) builder.applySetting("hibernate.connection.password", dbPass);

            // This builds the registry, applying any settings from hibernate.cfg.xml and overrides
            // It's required for creating MetadataSources in newer Hibernate versions
            registry = builder.build();

            // MetadataSources loads all entity classes and mappings from the registry
            // and is the starting point for constructing the Hibernate Metadata model
            MetadataSources sources = new MetadataSources(registry);

            // Metadata contains all the entity bindings and configuration needed by Hibernate
            // This is required before creating the actual SessionFactory
            Metadata metadata = sources.getMetadataBuilder().build();

            // Build SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();
            logger.info("Hibernate SessionFactory created with registry/bootstrap config.");

        } catch (Exception e) {
            logger.error("Hibernate initialization failed: {}", e.getMessage(), e);
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
            throw new ExceptionInInitializerError(e);
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