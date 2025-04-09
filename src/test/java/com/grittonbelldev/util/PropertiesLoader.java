package com.grittonbelldev.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This interface contains a default method that can be used anywhere a Properties
 * object is needed to be loaded.
 *
 * Implementing classes will get a logger whose name is the runtime class name,
 * ensuring that log messages indicate the correct source.
 *
 * @author Justin Gritton-Bell
 */
public interface PropertiesLoader {

    /**
     * Provides a logger with the name of the implementing class.
     * Using a default method here allows each implementing class to log under its own name.
     *
     * @return a Logger instance for the current object's class.
     */
    default Logger getLogger() {
        return LogManager.getLogger(this.getClass());
    }

    /**
     * Loads a properties file into a Properties instance.
     * If the file is not found or cannot be loaded, appropriate error messages are logged.
     *
     * @param propertiesFilePath a path to a file on the java classpath
     * @return a populated Properties instance or an empty Properties instance if
     *         the file path was not found.
     */
    default Properties loadProperties(String propertiesFilePath) {
        Properties properties = new Properties();

        try (InputStream input = this.getClass().getResourceAsStream(propertiesFilePath)) {
            if (input == null) {
                getLogger().error("The properties file '{}' was not found.", propertiesFilePath);
                return properties;
            }

            properties.load(input);
            getLogger().info("Loaded properties file '{}'.", propertiesFilePath);
        } catch (IOException ioException) {
            getLogger().error("Can't load the properties file.", ioException);
        } catch (Exception exception) {
            getLogger().error("There was a problem loading the properties file.", exception);
        }

        return properties;
    }
}