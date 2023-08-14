package pl.dundersztyc.fitnessapp.food.externalapi.edamam.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ProductList {

    @JsonProperty("hints")
    private JsonNode hints;

    public List<String> getProductIds(int numberOfProducts) {
        LinkedHashSet<String> productIds = new LinkedHashSet<>();
        numberOfProducts = Math.min(numberOfProducts, hints.size());

        for (int i = 0; i < hints.size() && productIds.size() < numberOfProducts; ++i) {
            productIds.add(
                    hints.get(i).path("food").path("foodId").asText()
            );
        }
        return new ArrayList<>(productIds);
    }
}
