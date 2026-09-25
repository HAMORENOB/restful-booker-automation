package com.harold.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadConfiguration();
    }

    private ConfigManager() {
    }

    private static void loadConfiguration() {
        String environment = System.getProperty("env", "qa");
        String resourcePath = "config/" + environment + ".properties";

        try (InputStream inputStream =
                     ConfigManager.class
                             .getClassLoader()
                             .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "No se encontró el archivo de configuración: "
                                + resourcePath
                );
            }

            PROPERTIES.load(inputStream);

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "No fue posible cargar la configuración: "
                            + resourcePath,
                    exception
            );
        }
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "No existe un valor configurado para: " + key
            );
        }

        return value;
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getUsername() {
        return get("username");
    }

    public static String getPassword() {
        return get("password");
    }
}