package pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class ActivityTypeTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateCaloriesBurned")
    void calculateCaloriesBurned(ActivityType activity, long minutes, Weight weight, long expectedCaloriesBurned) {
        assertThat(activity.calculateCaloriesBurned(minutes, weight)).isEqualTo(expectedCaloriesBurned);
    }

    @ParameterizedTest
    @MethodSource("invalidParams")
    void shouldThrowWhenCalculateCaloriesBurnedWithInvalidMinutes(long minutes) {
        assertThrows(IllegalArgumentException.class, () -> {
           ActivityType.SOCCER.calculateCaloriesBurned(minutes, Weight.fromKg(70));
        });
    }

    private static Stream<Arguments> provideInputAndResultForCalculateCaloriesBurned() {
        return Stream.of(
                Arguments.of(ActivityType.BADMINTON, 30, Weight.fromKg(70), 158),
                Arguments.of(ActivityType.SWIMMING, 60, Weight.fromKg(50), 352),
                Arguments.of(ActivityType.BASKETBALL, 120, Weight.fromKg(105), 1266)
        );
    }

    private static Stream<Arguments> invalidParams() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1)
        );
    }
}