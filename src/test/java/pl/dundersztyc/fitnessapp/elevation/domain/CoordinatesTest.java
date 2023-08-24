package pl.dundersztyc.fitnessapp.elevation.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class CoordinatesTest {

    @ParameterizedTest
    @MethodSource("provideCorrectLatitudeAndLongitude")
    void latitudeAndLongitudeShouldBeInRange(double latitude, double longitude) {
        var coordinates = new Coordinates(latitude, longitude);
        assertThat(coordinates).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidLatitudeOrLongitude")
    void shouldThrowWhenLatitudeOrLongitudeIsNotInRange(double latitude, double longitude) {
        assertThrows(ConstraintViolationException.class,
                () -> new Coordinates(latitude, longitude));
    }

    @Test
    void shouldGetDistanceBetween() {
        Coordinates c1 = new Coordinates(10, 10);
        Coordinates c2 = new Coordinates(11, 10);
        Coordinates c3 = new Coordinates(12, 10);

        assertThat(c1.getDistanceBetween(c2).longValue()).isEqualTo(111195);
        assertThat(c1.getDistanceBetween(c3).longValue()).isEqualTo(111195 * 2);

        assertThat(c1.getDistanceBetween(c2).longValue()).isEqualTo(c2.getDistanceBetween(c1).longValue());
        assertThat(c1.getDistanceBetween(c3).longValue()).isEqualTo(c3.getDistanceBetween(c1).longValue());
    }

    private static Stream<Arguments> provideCorrectLatitudeAndLongitude() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(-90, 0),
                Arguments.of(90, 0),
                Arguments.of(0, -180),
                Arguments.of(0, 180)
        );
    }

    private static Stream<Arguments> invalidLatitudeOrLongitude() {
        return Stream.of(
                Arguments.of(-91, 0),
                Arguments.of(91, 0),
                Arguments.of(0, -181),
                Arguments.of(0, 181),
                Arguments.of(1111, 1111)
        );
    }

}