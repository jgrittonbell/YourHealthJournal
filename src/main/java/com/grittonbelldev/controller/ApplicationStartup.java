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
 * This servlet is automatically loaded when the web application starts.
 * It initializes resources such as the Hibernate SessionFactory and Cognito configuration.
 */
@WebServlet(name = "ApplicationStartup", urlPatterns = {}, loadOnStartup = 1)
public class ApplicationStartup extends HttpServlet implements PropertiesLoaderProd {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        initializeSessionFactory();
        loadAndStoreCognitoProperties();
    }

    /**
     * Initializes Hibernate's SessionFactory via SessionFactoryProvider.
     */
    private void initializeSessionFactory() {
        try {
            SessionFactoryProvider.createSessionFactory();
            logger.info("SessionFactoryProvider initialized successfully.");
        } catch (Exception e) {
            logger.error("Failed to initialize SessionFactory: {}", e.getMessage(), e);
            throw new RuntimeException("Startup failed due to Hibernate error", e);
        }
    }

    /**
     * Loads properties from AWS Secrets Manager and cognito.properties,
     * and makes them available via ServletContext.
     */
    private void loadAndStoreCognitoProperties() {
        try {
            Properties properties = loadProperties("/cognito.properties");

            Map<String, String> secrets = SecretsManagerUtil.getSecretAsMap("yhjSecrets");
            String clientId = secrets.get("cognitoClientID");
            String clientSecret = secrets.get("cognitoClientSecret");

            if (clientId == null || clientSecret == null) {
                logger.error("Missing required Cognito secrets. Cannot continue startup.");
                throw new RuntimeException("Missing Cognito secrets");
            }

            ServletContext context = getServletContext();
            context.setAttribute("client.id", clientId);
            context.setAttribute("client.secret", clientSecret);
            context.setAttribute("oauthURL", properties.getProperty("oauthURL"));
            context.setAttribute("loginURL", properties.getProperty("loginURL"));
            context.setAttribute("redirectURL", properties.getProperty("redirectURL"));
            context.setAttribute("region", properties.getProperty("region"));
            context.setAttribute("poolId", properties.getProperty("poolId"));

            logger.info("Cognito properties loaded successfully.");

        } catch (IOException ioException) {
            logger.error("IOError loading Cognito properties: {}", ioException.getMessage(), ioException);
        } catch (Exception exception) {
            logger.error("Error loading Cognito config: {}", exception.getMessage(), exception);
        }
    }

    /**
     * Called when the application shuts down.
     * Closes the Hibernate SessionFactory if initialized.
     */
    @Override
    public void destroy() {
        try {
            if (SessionFactoryProvider.getSessionFactory() != null) {
                SessionFactoryProvider.getSessionFactory().close();
                logger.info("SessionFactory closed.");
            }
        } catch (Exception e) {
            logger.error("Error shutting down SessionFactory: {}", e.getMessage(), e);
        }
    }
}
