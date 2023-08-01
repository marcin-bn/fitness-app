package pl.dundersztyc.fitnessapp.calculators.bfi.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BfiTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateBfi")
    void calculateBfi(Gender gender, long neckCirc, long waistCirc, long hipCirc, Height height, double expectedBfi) {
        Bfi bfi = new Bfi(gender, neckCirc, waistCirc, hipCirc, height);
        assertThat(bfi.getValue()).isEqualTo(expectedBfi);
    }

    @ParameterizedTest
    @MethodSource("invalidBfiParams")
    void shouldThrowWhenCalculateBfiWithInvalidParams(Gender gender, long neckCirc, long waistCirc, long hipCirc, Height height) {
        assertThrows(ConstraintViolationException.class, () -> {
            Bfi bfi = new Bfi(gender, neckCirc, waistCirc, hipCirc, height);
        });
    }

    private static Stream<Arguments> provideInputAndResultForCalculateBfi() {
        return Stream.of(
                Arguments.of(Gender.MAN, 32, 85, 90, Height.fromCm(186), 19.53),
                Arguments.of(Gender.WOMAN, 27, 70, 90, Height.fromCm(167), 24.33),
                Arguments.of(Gender.MAN, 30, 90, 100, Height.fromCm(172), 26.55)
        );
    }

    private static Stream<Arguments> invalidBfiParams() {
        return Stream.of(
                Arguments.of(Gender.MAN, -1, 1, 1, Height.fromCm(1)),
                Arguments.of(Gender.MAN, 1, -1, 1, Height.fromCm(1)),
                Arguments.of(Gender.MAN, 1, 1, -1, Height.fromCm(1)),
                Arguments.of(null, 1, 1, 1, Height.fromCm(1))
        );
    }


}