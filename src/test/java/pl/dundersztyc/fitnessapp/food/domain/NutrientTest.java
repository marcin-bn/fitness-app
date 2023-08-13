package pl.dundersztyc.fitnessapp.food.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NutrientTest {

    @Test
    void shouldConvertGramsToOtherMeasures() {
        Nutrient nutrient = Nutrient.fromGrams(1.5);

        assertThat(nutrient.getMicrograms()).isEqualTo(1500000);
        assertThat(nutrient.getMilligrams()).isEqualTo(1500);
        assertThat(nutrient.getGrams()).isEqualTo(1.5);
    }

    @Test
    void shouldConvertMilligramsToOtherMeasures() {
        Nutrient nutrient = Nutrient.fromMilligrams(450.5);

        assertThat(nutrient.getMicrograms()).isEqualTo(450500);
        assertThat(nutrient.getMilligrams()).isEqualTo(450.5);
        assertThat(nutrient.getGrams()).isEqualTo(0.4505);
    }

    @Test
    void shouldConvertMicrogramsToOtherMeasures() {
        Nutrient nutrient = Nutrient.fromMicrograms(1500.3);

        assertThat(nutrient.getMicrograms()).isEqualTo(1500.3);
        assertThat(nutrient.getMilligrams()).isEqualTo(1.5003);
        assertThat(nutrient.getGrams()).isEqualTo(0.0015003);
    }

    @Test
    void shouldThrowWhenCreateNutrientWithValueLessThan0() {
        assertThrows(ConstraintViolationException.class, () -> {Nutrient.fromMicrograms(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Nutrient.fromMilligrams(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Nutrient.fromGrams(-1);});
    }

}