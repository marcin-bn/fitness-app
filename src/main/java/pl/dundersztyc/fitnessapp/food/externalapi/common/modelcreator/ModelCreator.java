package pl.dundersztyc.fitnessapp.food.externalapi.common.modelcreator;

import org.json.JSONObject;
import lombok.NonNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ModelCreator {

    private static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    public static <T> T getForObject(@NonNull String url, @NonNull Class<T> clazz) {
        return new RestTemplate().getForObject(url, clazz);
    }

    public static <T> T postForObject(@NonNull String url, @NonNull JSONObject jsonRequest, @NonNull Class<T> clazz) {
        var headers = new HttpHeaders();
        headers.setContentType(CONTENT_TYPE);
        HttpEntity<String> request = new HttpEntity<>(jsonRequest.toString(), headers);

        return new RestTemplate().postForObject(url, request, clazz);
    }
}