package br.com.projeto.utils;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

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

    public static void sendJsonResponse(String json, HttpServletResponse response) throws IOException {
        Utils.setJsonResponseHeaders(response);

        response.getWriter().write(json);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static void setJsonResponseHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

}
