package pl.dundersztyc.fitnessapp.calculators.bmi.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class BmiCategoryTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForDetermineCategory")
    void determineCategory(double bmi, BmiCategory expectedCategory) {
        assertThat(BmiCategory.determineCategory(bmi)).isEqualTo(expectedCategory);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-20.0, 100.0})
    void shouldThrowWhenDetermineCategoryWithInvalidBmi(double invalidBmi) {
        assertThrows(IllegalArgumentException.class, () -> {
            BmiCategory.determineCategory(invalidBmi);
        });
    }


    private static Stream<Arguments> provideInputAndResultForDetermineCategory() {
        return Stream.of(
                Arguments.of(15.5, BmiCategory.UNDER_WEIGHT),
                Arguments.of(20.0, BmiCategory.NORMAL_WEIGHT),
                Arguments.of(28.0, BmiCategory.OVER_WEIGHT),
                Arguments.of(35.0, BmiCategory.OBESE)
        );
    }

}