package pl.dundersztyc.fitnessapp.calculators.bmi.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BmiTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateBmi")
    void calculateBmi(double weightInKg, double heightInM, double expectedBmi) {
        Bmi bmi = new Bmi(Weight.fromKg(weightInKg), Height.fromM(heightInM));
        assertThat(bmi.getValue()).isEqualTo(expectedBmi);
    }

    @ParameterizedTest
    @MethodSource("invalidBmiParams")
    void shouldThrowWhenCalculateBmiWithWeightOrHeightLessThanOrEqualTo0(double weightInKg, double heightInM) {
        assertThrows(ConstraintViolationException.class, () -> {
           Bmi bmi = new Bmi(Weight.fromKg(weightInKg), Height.fromM(heightInM));
        });
    }


    private static Stream<Arguments> provideInputAndResultForCalculateBmi() {
        return Stream.of(
                Arguments.of(100.0, 2.0, 25.0),
                Arguments.of(70.9, 1.78, 22.38),
                Arguments.of(62.65, 1.91, 17.17)
        );
    }

    private static Stream<Arguments> invalidBmiParams() {
        return Stream.of(
                Arguments.of(0.0, 15.5),
                Arguments.of(15.5, 0.0),
                Arguments.of(0.0, 0.0),
                Arguments.of(-2.5, 15.5),
                Arguments.of(2.5, -15.5),
                Arguments.of(-15.5, -15.5)
        );
    }
}