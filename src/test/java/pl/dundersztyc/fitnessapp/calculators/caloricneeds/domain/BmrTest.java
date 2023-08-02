package pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BmrTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateBmr")
    void calculateBmr(long age, Weight weight, Height height, Gender gender, long expectedBmr) {
        assertThat(Bmr.calculateBmr(age, weight, height, gender)).isEqualTo(expectedBmr);
    }

    @ParameterizedTest
    @MethodSource("invalidAge")
    void shouldThrowWhenCalculateCaloricNeedsWithInvalidAge(long age) {
        assertThrows(IllegalArgumentException.class, () -> {
            Bmr.calculateBmr(age, Weight.fromKg(65), Height.fromCm(180), Gender.MAN);
        });
    }

    private static Stream<Arguments> provideInputAndResultForCalculateBmr() {
        return Stream.of(
                Arguments.of(25, Weight.fromKg(65), Height.fromCm(180), Gender.MAN, 1655),
                Arguments.of(40, Weight.fromKg(60), Height.fromCm(170), Gender.WOMAN, 1301),
                Arguments.of(20, Weight.fromKg(55), Height.fromCm(168), Gender.WOMAN, 1339)
        );
    }

    private static Stream<Arguments> invalidAge() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of(-100)
        );
    }
}