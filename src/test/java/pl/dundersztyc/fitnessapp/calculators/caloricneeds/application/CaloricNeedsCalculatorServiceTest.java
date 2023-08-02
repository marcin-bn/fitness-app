package pl.dundersztyc.fitnessapp.calculators.caloricneeds.application;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.ActivityFrequency;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.BodyWeightGoal;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class CaloricNeedsCalculatorServiceTest {

    private final CaloricNeedsCalculatorService calculatorService = new CaloricNeedsCalculatorService();

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateCaloricNeeds")
    void calculateCaloricNeeds(long age, Gender gender, Height height, Weight weight,
                               ActivityFrequency frequency, BodyWeightGoal weightGoal, long expectedCalories) {
        assertThat(calculatorService.calculateCaloricNeeds(age, gender, height, weight, frequency, weightGoal)).isEqualTo(expectedCalories);
    }

    // TODO: add validation in service / add VO -> change RuntimeException to more specific exception
    @ParameterizedTest
    @MethodSource("invalidParams")
    void shouldThrowWhenCalculateCaloricNeedsWithInvalidParams(long age, Gender gender, Height height, Weight weight,
                                                               ActivityFrequency frequency, BodyWeightGoal weightGoal) {
        assertThrows(RuntimeException.class, () -> {
            calculatorService.calculateCaloricNeeds(age, gender, height, weight, frequency, weightGoal);
        });
    }

    private static Stream<Arguments> provideInputAndResultForCalculateCaloricNeeds() {
        return Stream.of(
                Arguments.of(
                        25, Gender.MAN, Height.fromCm(180),
                        Weight.fromKg(95), ActivityFrequency.ACTIVE, BodyWeightGoal.WEIGHT_LOSS, 2530
                ),
                Arguments.of(
                        20, Gender.WOMAN, Height.fromCm(160),
                        Weight.fromKg(50), ActivityFrequency.MODERATE, BodyWeightGoal.MILD_WEIGHT_GAIN, 2065
                ),
                Arguments.of(
                        40, Gender.WOMAN, Height.fromCm(165),
                        Weight.fromKg(45), ActivityFrequency.LITTLE, BodyWeightGoal.WEIGHT_GAIN, 1844
                )
        );
    }

    private static Stream<Arguments> invalidParams() {
        return Stream.of(
                Arguments.of(
                        -25, Gender.MAN, Height.fromCm(180),
                        Weight.fromKg(95), ActivityFrequency.ACTIVE, BodyWeightGoal.WEIGHT_LOSS
                ),
                Arguments.of(
                        20, null, Height.fromCm(160),
                        Weight.fromKg(50), ActivityFrequency.MODERATE, BodyWeightGoal.MILD_WEIGHT_GAIN
                )
        );
    }
}