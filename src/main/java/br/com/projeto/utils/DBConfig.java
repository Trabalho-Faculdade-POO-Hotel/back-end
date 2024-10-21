package br.com.projeto.utils;

import java.util.Properties;

public class DBConfig {
    private static final String RESOURCE_FILE_NAME = "db.properties";
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    private DBConfig(String url, String username, String password, String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public static DBConfig getDefaultConfig() {
        Properties properties = Utils.getProperties(RESOURCE_FILE_NAME);

        if (properties == null) {
            return null;
        }

        return new DBConfig(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"),
                properties.getProperty("db.driverClassName"));
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

}
