package org.najoa.configs;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvManager {
    private static final Dotenv dotenv = Dotenv.configure()
            .filename(".env")
            .ignoreIfMissing() // Ensures no crash if .env is missing
            .load();

    /**
     * Retrieves the value for the specified key.
     * Priority:
     * 1. System environment variables (e.g., set via OS or CI/CD).
     * 2. Variables from the .env file.
     * 3. Default value (if provided).
     *
     * @param key          The key to retrieve.
     * @param defaultValue The default value if the key is not found.
     * @return The resolved value.
     */
    public static String get(String key, String defaultValue) {
        // Check system environment variables first
        String value = System.getenv(key);

        // Fallback to .env file if not in system environment variables
        if (value == null || value.isEmpty()) {
            value = dotenv.get(key);
        }

        // Fallback to default value if not found in .env or system env
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    /**
     * Retrieves the value for the specified key.
     * Priority:
     * 1. System environment variables.
     * 2. Variables from the .env file.

     * Throws an exception if the key is not found.
     *
     * @param key The key to retrieve.
     * @return The resolved value.
     */
    public static String get(String key) {
        String value = get(key, null); // Call the method with defaultValue as null
        if (value == null) {
            throw new IllegalArgumentException("Missing required environment variable: " + key);
        }
        return value;
    }
}