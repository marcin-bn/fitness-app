package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.food.domain.Nutrient;
import pl.dundersztyc.fitnessapp.food.domain.Product;

public class ProductTestData {

    public static Product.ProductBuilder defaultProduct() {
        return Product.builder()
                .id("123456789")
                .name("Nutella")
                .kcal(500.0)
                .fat(Nutrient.fromGrams(1.5))
                .proteins(Nutrient.fromGrams(3.5))
                .carbohydrates(Nutrient.fromGrams(41.5))
                .sugars(Nutrient.fromGrams(31.5))
                .salt(Nutrient.fromGrams(1.5))
                .vitaminA(Nutrient.fromMicrograms(30))
                .vitaminC(Nutrient.fromMicrograms(1.5))
                .vitaminD(Nutrient.fromMicrograms(2.5))
                .vitaminE(Nutrient.fromMicrograms(3.5))
                .vitaminK(Nutrient.fromMicrograms(4.5))
                .sodium(Nutrient.fromMicrograms(5.5))
                .calcium(Nutrient.fromMicrograms(6.5))
                .potassium(Nutrient.fromMicrograms(7.5))
                .magnesium(Nutrient.fromMicrograms(8.5))
                .iron(Nutrient.fromMicrograms(9.5));
    }
}
