package br.com.projeto.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.projeto.core.base.ClientException;

public class Utils {
    private static ObjectMapper mapper = new ObjectMapper();

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

    public static String createErrorJsonPayload(String message) {
        return String.format("{ \"error\": \"%s\" }", message);
    }

    public static String createErrorJsonPayload(ClientException e) {
        return Utils.createErrorJsonPayload(e.getReason());
    }

    public static String convertObjectToJson(Object obj) throws JsonProcessingException {
        return Utils.mapper.writeValueAsString(obj);
    }

    public static void sendJsonResponse(String json, HttpServletResponse response) throws IOException {
        Utils.setJsonResponseHeaders(response);

        response.getWriter().write(json);
    }

    public static void setJsonResponseHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public static String getJsonFromRequestBody(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = req.getReader().readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return sb.toString();
    }

    public static <T> T getEntityFromJson(String json, Class<? extends T> entityClass) throws IOException {
        return Utils.mapper.readValue(json, entityClass);
    }

    public static String convertDateToISOString(Date date, boolean withTime) {
        if (date == null) {
            return null;
        }

        TimeZone tz = TimeZone.getTimeZone("UTC");

        String dateFormaString = withTime ? "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" : "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(dateFormaString);
        df.setTimeZone(tz);

        return df.format(date);
    }

}
