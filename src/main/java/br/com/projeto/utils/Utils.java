package br.com.projeto.utils;

import java.util.Properties;

public class Utils {

    public static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();

        try {
            properties.load(Utils.class.getClassLoader().getResourceAsStream(propertiesFile));
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return properties;
    }

}
