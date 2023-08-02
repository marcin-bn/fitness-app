package pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class ActivityFrequencyTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForGetBasicCaloricNeeds")
    void getBasicCaloricNeeds(ActivityFrequency frequency, long bmr, long expectedBasicCalories) {
        assertThat(frequency.getBasicCaloricNeeds(bmr)).isEqualTo(expectedBasicCalories);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -100})
    void shouldThrowWhenGetBasicCaloricNeedsWithInvalidBmr(long bmr) {
        assertThrows(IllegalArgumentException.class, () -> {
            ActivityFrequency.ACTIVE.getBasicCaloricNeeds(bmr);
        });
    }


    private static Stream<Arguments> provideInputAndResultForGetBasicCaloricNeeds() {
        return Stream.of(
                Arguments.of(ActivityFrequency.ACTIVE, 1000, 1550),
                Arguments.of(ActivityFrequency.EXTRA_ACTIVE, 2000, 3800),
                Arguments.of(ActivityFrequency.LITTLE, 1500, 1800),
                Arguments.of(ActivityFrequency.MODERATE, 1256, 1840)
        );
    }

}