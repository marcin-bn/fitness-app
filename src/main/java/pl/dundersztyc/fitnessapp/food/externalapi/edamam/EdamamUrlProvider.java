package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class EdamamUrlProvider {

    @Value("${external-api.edamam.app-id}")
    private String appId;

    @Value("${external-api.edamam.app-key}")
    private String appKey;

    public String getBasicProductNutrientsURL() {
        return String.format("https://api.edamam.com/api/food-database/v2/nutrients?app_id=%s&app_key=%s", appId, appKey);
    }

    public String getBasicProductParserURL() {
        return String.format("https://api.edamam.com/api/food-database/v2/parser?app_id=%s&app_key=%s", appId, appKey);
    }
}
