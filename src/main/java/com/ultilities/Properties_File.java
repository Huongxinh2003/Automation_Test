package com.ultilities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Properties_File {
    private static Properties properties;
    private static FileInputStream fileIn;
    private static FileOutputStream fileOut;

    static String projectPath = System.getProperty("user.dir") + "/";
    private static final String propertiesFilePathRoot = "src/test/resources/configs.properties";

    public static void setPropertiesFile() {
        properties = new Properties();
        try {
            fileIn = new FileInputStream(projectPath + propertiesFilePathRoot);
            InputStreamReader reader = new InputStreamReader(fileIn, StandardCharsets.UTF_8);
            properties.load(reader);
        } catch (Exception exp) {
            System.out.println("Lỗi khi đọc file properties: " + exp.getMessage());
            exp.printStackTrace();
        }
    }

    // Lấy giá trị theo key
    public static String getPropValue(String KeyProp) {
        String value = null;
        try {
            value = properties.getProperty(KeyProp);
            System.out.println("Giá trị của key '" + KeyProp + "': " + value);
            return value;
        } catch (Exception exp) {
            System.out.println("Lỗi khi lấy value: " + exp.getMessage());
            exp.printStackTrace();
        }
        return value;
    }

    public static void setPropValue(String KeyProp, String Value) {
        try {
            fileOut = new FileOutputStream(projectPath + propertiesFilePathRoot);
            OutputStreamWriter writer = new OutputStreamWriter(fileOut, StandardCharsets.UTF_8);
            properties.setProperty(KeyProp, Value);
            properties.store(writer, "Đã cập nhật key: " + KeyProp);
            System.out.println("✅ Cập nhật key '" + KeyProp + "' thành công.");
        } catch (Exception exp) {
            System.out.println("Lỗi khi ghi file properties: " + exp.getMessage());
            exp.printStackTrace();
        }
    }
}
