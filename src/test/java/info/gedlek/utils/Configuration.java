package info.gedlek.utils;

import lombok.extern.slf4j.Slf4j; // Opcjonalnie do logowania
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Configuration.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration properties", e);
        }
    }

    public static String getBaseUri() {
        return System.getProperty("api.base.uri", properties.getProperty("api.base.uri"));
    }
}