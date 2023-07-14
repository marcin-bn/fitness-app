package pl.dundersztyc.fitnessapp.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    public static String toJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, Class<T> objectClass) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, objectClass);
    }
}
