package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import org.springframework.stereotype.Component;

@Component
class OpenFoodFactsUrlProvider {

    public String getProductURL() {
        return "https://world.openfoodfacts.org/api/v2/product/";
    }
}
