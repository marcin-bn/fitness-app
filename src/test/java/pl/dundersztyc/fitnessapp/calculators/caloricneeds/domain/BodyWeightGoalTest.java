package pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class BodyWeightGoalTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForGetCaloricNeeds")
    void getCaloricNeeds(BodyWeightGoal weightGoal, long basicCaloricNeeds, long expectedCalories) {
        assertThat(weightGoal.getCaloricNeeds(basicCaloricNeeds)).isEqualTo(expectedCalories);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -100})
    void shouldThrowWhenGetBasicCaloricNeedsWithInvalidBmr(long basicCaloricNeeds) {
        assertThrows(IllegalArgumentException.class, () -> {
            BodyWeightGoal.WEIGHT_GAIN.getCaloricNeeds(basicCaloricNeeds);
        });
    }


    private static Stream<Arguments> provideInputAndResultForGetCaloricNeeds() {
        return Stream.of(
                Arguments.of(BodyWeightGoal.WEIGHT_LOSS, 2000, 1500),
                Arguments.of(BodyWeightGoal.MILD_WEIGHT_LOSS, 2000, 1750),
                Arguments.of(BodyWeightGoal.MAINTAIN_WEIGHT, 2000, 2000),
                Arguments.of(BodyWeightGoal.MILD_WEIGHT_GAIN, 2000, 2250),
                Arguments.of(BodyWeightGoal.WEIGHT_GAIN, 2000, 2500)
        );
    }

}