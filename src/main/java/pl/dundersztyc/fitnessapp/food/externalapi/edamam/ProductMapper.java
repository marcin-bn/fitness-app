package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.domain.ProductNutritionFacts;

@Component("edamam-p-mapper")
@Qualifier("edamam")
class ProductMapper {

    public Product mapToProduct(String productId, String productName, ProductNutritionFacts productNutritionFacts) {
        return Product.builder()
                .id(new Product.ProductId(productId))
                .name(productName)
                .nutritionFacts(productNutritionFacts)
                .build();
    }
}
