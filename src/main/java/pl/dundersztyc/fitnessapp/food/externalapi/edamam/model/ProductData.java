package pl.dundersztyc.fitnessapp.food.externalapi.edamam.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.Map;


public class ProductData {

    @JsonProperty("ingredients")
    private JsonNode ingredients;

    @JsonProperty("totalNutrients")
    @Getter
    private Map<String, SingleNutrient> totalNutrients;

    public String getProductName() {
        return ingredients.get(0).path("parsed").get(0).path("food").asText();
    }
}
