package pl.dundersztyc.fitnessapp.food.adapter.in;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.dundersztyc.fitnessapp.common.ProductTestData.defaultProduct;

class ProductResponseTest {

    @Test
    void shouldGetProductResponse() {
        var productResponse = ProductResponse.of(
                defaultProduct().vitaminA(Nutrient.fromMicrograms(100)).build());

        assertThat(productResponse).isNotNull();

        var vitaminA = productResponse.getVitaminA();
        assertThat(vitaminA.exists()).isTrue();
        assertThat(vitaminA.value().quantity()).isEqualTo(100);
        assertThat(vitaminA.value().unit()).isEqualTo("µg");
    }

}