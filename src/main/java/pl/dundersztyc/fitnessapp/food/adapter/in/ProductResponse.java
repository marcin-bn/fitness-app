package pl.dundersztyc.fitnessapp.food.adapter.in;

import lombok.*;
import pl.dundersztyc.fitnessapp.food.domain.Product;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private String id;
    private String name;
    private NutritionFactsResponse nutritionFacts;

    public static ProductResponse of(Product product) {
        var nutritionFacts = product.getNutritionFacts();
        return ProductResponse.builder()
                .id(product.getId().value())
                .name(product.getName())
                .nutritionFacts(NutritionFactsResponse.of(nutritionFacts))
                .build();
    }


}

