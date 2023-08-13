package pl.dundersztyc.fitnessapp.food.externalapi.edamam.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class ProductParserData {

    @JsonProperty("text")
    private String text;

    @JsonProperty("hints")
    private JsonNode hints;

    public String getFoodId() {
        return hints.get(0).path("food").path("foodId").asText();
    }
}
