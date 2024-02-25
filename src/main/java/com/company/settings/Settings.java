package com.company.settings;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static final String SETTINGS_FILE = "settings.txt";
    private static Properties properties;

    public static void connect() {
        try (FileReader reader = new FileReader(SETTINGS_FILE)) {
            properties = new Properties();
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        connect();
        return Integer.parseInt(properties.getProperty("PORT"));
    }

    public String getHost() {
        connect();
        return properties.getProperty("HOST");
    }
}
