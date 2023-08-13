package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ProductData {

    @JsonProperty("product")
    private JsonNode productInfo;

    public Map<String, Object> getNutrients() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(productInfo.path("nutriments"), new TypeReference<Map<String, Object>>(){});
    }

    public boolean isConvertibleToProduct() {
        try {
            getNutrients();
        }
        catch (NullPointerException ignored) {
            return false;
        }
        return true;
    }

    public String getProductName() {
        return productInfo.path("product_name").asText();
    }

}
