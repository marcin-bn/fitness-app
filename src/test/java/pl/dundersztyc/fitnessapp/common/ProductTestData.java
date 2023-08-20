package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.food.domain.Product;

import static pl.dundersztyc.fitnessapp.common.ProductNutritionFactsData.defaultProductNutritionFacts;

public class ProductTestData {

    public static Product.ProductBuilder defaultProduct() {
        return Product.builder()
                .id(new Product.ProductId("123456789"))
                .name("Nutella")
                .nutritionFacts(defaultProductNutritionFacts().build());
    }


}
