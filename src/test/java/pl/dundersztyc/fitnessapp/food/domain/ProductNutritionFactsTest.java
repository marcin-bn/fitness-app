package pl.dundersztyc.fitnessapp.food.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.dundersztyc.fitnessapp.common.ProductNutritionFactsData.defaultProductNutritionFacts;

class ProductNutritionFactsTest {

    @Test
    void shouldMultiplyNutritionFacts() {
        var nutritionFacts = defaultProductNutritionFacts()
                .kcal(500.0)
                .vitaminA(Nutrient.fromMicrograms(200))
                .vitaminC(null)
                .build();

        var afterMultiplication = nutritionFacts.multiply(1.25);

        assertThat(afterMultiplication.getKcal()).isEqualTo(625);
        assertThat(afterMultiplication.getVitaminA().getMicrograms()).isEqualTo(250);
        assertThat(afterMultiplication.getVitaminC()).isNull();
    }
}