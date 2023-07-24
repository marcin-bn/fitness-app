package pl.dundersztyc.fitnessapp.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = com.fasterxml.jackson.databind.json.JsonMapper.builder().addModule(new JavaTimeModule()).build();

    public static String toJsonString(final Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, Class<T> objectClass) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, objectClass);
    }

    public static <T> T fromJson(final String json, TypeReference<T> typeRef) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, typeRef);
    }
}
