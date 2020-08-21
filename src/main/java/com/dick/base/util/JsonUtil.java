package com.dick.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUtil {
    public static void writeResponse(Object value, int status) throws IOException {
        if ( ! (value instanceof String) ) {
            value = serialize(value);
        }
        HttpServletResponse response = ServletUtil.currentResponse();
        response.setStatus(status);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().print(value);
    }
    public static String serialize(Object value) {
        ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T deserialize(String json, Class<T> clazz) {
        ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
