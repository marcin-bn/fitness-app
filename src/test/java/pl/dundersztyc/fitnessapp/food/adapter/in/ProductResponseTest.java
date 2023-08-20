package pl.dundersztyc.fitnessapp.food.adapter.in;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.ProductNutritionFactsData.defaultProductNutritionFacts;
import static pl.dundersztyc.fitnessapp.common.ProductTestData.defaultProduct;

class ProductResponseTest {

    @Test
    void shouldGetProductResponse() {
        var productResponse = ProductResponse.of(
                defaultProduct()
                        .nutritionFacts(defaultProductNutritionFacts().vitaminA(Nutrient.fromMicrograms(100)).build())
                        .build());

        assertThat(productResponse).isNotNull();

        var nutritionFacts = productResponse.getNutritionFacts();
        var vitaminA = nutritionFacts.getVitaminA();
        assertThat(vitaminA.exists()).isTrue();
        assertThat(vitaminA.value().quantity()).isEqualTo(100);
        assertThat(vitaminA.value().unit()).isEqualTo("µg");
    }

}