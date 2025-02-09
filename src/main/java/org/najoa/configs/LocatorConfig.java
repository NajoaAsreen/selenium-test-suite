package org.najoa.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class LocatorConfig {
    private static final Logger logger = LogManager.getLogger(LocatorConfig.class);
    private static final Properties locators = new Properties();

    static {
        try {
            // Load the default locators file
            loadLocators("locators.properties");

            // Get application names from the environment file or system property
            String appNames = EnvManager.get("APP_NAMES");
            if (appNames.isEmpty()) {
                logger.warn("No application names found in APP_NAMES environment variable, using default locators only.");
            } else {
                // Split the comma-separated list of application names into a list
                List<String> appList = new ArrayList<>(Arrays.asList(appNames.split(",")));

                // Iterate through the list of app names and load the corresponding locator files
                for (String appName : appList) {
                    String locatorFileName = appName.trim() + "_locators.properties"; // app1_locators.properties, etc.
                    loadLocators(locatorFileName);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to initialize locator configuration", e);
            throw new RuntimeException("Locator configuration initialization failed", e);
        }
    }

    // Method to load locators from a properties file
    private static void loadLocators(String fileName) throws IOException {
        try (InputStream input = LocatorConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                logger.warn("Locator file not found: " + fileName);
            } else {
                locators.load(input);
                logger.info("Loaded locator file: " + fileName);
            }
        }
    }

    public static By getLocator(String key) {
        return getLocator(key, null); // Calls the overloaded method with `null` as envKey
    }

    public static By getLocator(String key, String envKey) {
        String locatorValue = locators.getProperty(key);
        if (locatorValue != null) {
            // Check if an environment variable key is provided
            if (envKey != null && !envKey.isEmpty()) {
                String envValue = EnvManager.get(envKey); // Get the value from the environment
                logger.debug("envValue:{}",envValue);
                if (!envValue.isEmpty()) {
                    // Replace the placeholder with the environment variable value
                    locatorValue = locatorValue.replace("${" + envKey + "}", envValue);
                }
            }
            logger.info("key: {}, locatorValue:{},", key, locatorValue);
            String[] parts = locatorValue.split("=", 2);
            String strategy = parts[0];
            String value = parts[1];

            switch (strategy.toLowerCase()) {
                case "id":
                    return By.id(value);
                case "xpath":
                    return By.xpath(value);
                case "css":
                    return By.cssSelector(value);
                case "name":
                    return By.name(value);
                case "linktext":
                    return By.linkText(value);
                case "partiallinktext":
                    return By.partialLinkText(value);
                case "tagname":
                    return By.tagName(value);
                case "classname":
                    return By.className(value);
                default:
                    throw new IllegalArgumentException("Unsupported locator strategy: " + strategy);
            }
        }
        throw new IllegalArgumentException("Locator not found for key: " + key);
    }
}
