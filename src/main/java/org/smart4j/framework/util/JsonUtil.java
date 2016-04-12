package org.smart4j.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by david.cai on 2016/4/12.
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * POJO转换为JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("convert POJO to JSON failure ", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T fromJson(String json, Class<T> type){
        T obj = null;
        try {
            obj = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("convert JSON to POJO failure ", e);
            throw new RuntimeException(e);
        }
        return obj;
    }
}
