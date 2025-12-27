package info.gedlek.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Configuration {

    BASE_URI("api.base.uri"),
    ;

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

    private final String key;

    Configuration(String key) {
        this.key = key;
    }
    public  String get() {
        return properties.getProperty(key);
    }
}