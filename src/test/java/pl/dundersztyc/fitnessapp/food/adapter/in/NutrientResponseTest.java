package pl.dundersztyc.fitnessapp.food.adapter.in;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class NutrientResponseTest {

    @Test
    void shouldGetNutrientResponseInGrams() {
        var nutrient = Nutrient.fromGrams(10.5);

        var nutrientResponse = NutrientResponse.of(nutrient, NutrientResponse.Unit.Gram);

        assertThat(nutrientResponse.quantity()).isEqualTo(10.5);
        assertThat(nutrientResponse.unit()).isEqualTo("g");
    }

    @Test
    void shouldGetNutrientResponseInMilligrams() {
        var nutrient = Nutrient.fromGrams(10.5);

        var nutrientResponse = NutrientResponse.of(nutrient, NutrientResponse.Unit.Milligram);

        assertThat(nutrientResponse.quantity()).isEqualTo(10500);
        assertThat(nutrientResponse.unit()).isEqualTo("mg");
    }

    @Test
    void shouldGetNutrientResponseInMicrograms() {
        var nutrient = Nutrient.fromGrams(10.5);

        var nutrientResponse = NutrientResponse.of(nutrient, NutrientResponse.Unit.Microgram);

        assertThat(nutrientResponse.quantity()).isEqualTo(10500000);
        assertThat(nutrientResponse.unit()).isEqualTo("µg");
    }

    @Test
    void shouldGetNutrientResponseWhenNutrientIsNull() {
        var nutrientResponse = NutrientResponse.of(null, NutrientResponse.Unit.Microgram);

        assertThat(nutrientResponse).isNull();
    }

    @Test
    void shouldThrowWhenUnitIsNull() {
        assertThrows(NullPointerException.class, () -> {
            NutrientResponse.of(Nutrient.fromGrams(10), null);
        });
    }

}