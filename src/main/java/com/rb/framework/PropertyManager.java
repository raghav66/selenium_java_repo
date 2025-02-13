package com.rb.framework;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyManager {
    static GlobalParams params = new GlobalParams();

    public static Map<String, String> getProps() {
        try {
            String basePath = System.getProperty("user.dir"); // Get the base directory of the project
            FileInputStream input = getFileInputStream(basePath);
            Properties prop = new Properties();
            prop.load(input);
            Map<String, String> properties = new HashMap<>();
            for (Object key : prop.keySet()) {
                properties.put(key.toString(), prop.getProperty(key.toString()));
            }
            input.close();
            return properties;
        } catch (Exception e) {
            System.out.println("Exception while loading properties: " + e.getMessage());
        }
        return null;
    }


    public  static FileInputStream getFileInputStream(String basePath) throws FileNotFoundException {
        String relativePath;
        String fileName;

        if (params.getExecutionEnv().equalsIgnoreCase("staging")) {
            relativePath = "src/main/resources/configData/staging";
            fileName = params.getClientName().toLowerCase() + "_staging_config.properties";
        } else if (params.getExecutionEnv().equalsIgnoreCase("production")) {
            relativePath = "src/main/resources/configData/production";
            fileName = params.getClientName().toLowerCase() + "_production_config.properties";
        } else if (params.getExecutionEnv().equalsIgnoreCase("uat")) {
            relativePath = "src/main/resources/configData/uat";
            fileName = params.getClientName().toLowerCase() + "_uat_config.properties";
        } else {
            throw new IllegalArgumentException("Invalid execution environment: " + params.getExecutionEnv());
        }

        String path = basePath + "/" + relativePath + "/" + fileName;
        File config = new File(path);
        return new FileInputStream(config.getAbsolutePath());
    }

    private static final String API_PROPERTIES_FILE_NAME = "src/main/resources/configData/staging/api_staging_config.properties";

    public static String getApiPropertyValue(String key) {
        Properties properties = new Properties();
        String value = null;

        try (FileInputStream fis = new FileInputStream(API_PROPERTIES_FILE_NAME)) {
            // Load the properties file
            properties.load(fis);

            // Retrieve the value associated with the specified key
            value = properties.getProperty(key);

            if (value == null) {
                throw new IllegalArgumentException("Key '" + key + "' not found in properties file.");
            }

        } catch (Exception e) {
            // Catch-all for any other unexpected exceptions
            System.err.println("An error occurred: " + e.getMessage());
        }

        return value;
    }

}


