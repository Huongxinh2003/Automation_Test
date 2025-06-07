package com.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BrowserConfig {
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    public static String getBrowserType() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            prop.load(fis);
            return prop.getProperty("browser", "chrome"); // mặc định chrome
        } catch (IOException e) {
            e.printStackTrace();
            return "chrome"; // fallback
        }
    }
}
