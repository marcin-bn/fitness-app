package pl.dundersztyc.fitnessapp.bodyweight.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.BodyWeightMeasurementTestData.defaultMeasurement;


public class BodyWeightProfileTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateIsProgressMade")
    void calculateIsProgressMade(BigDecimal startWeight, BigDecimal endWeight, boolean expectedProgress) {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);

        BodyWeightMeasurementWindow measurementWindow = new BodyWeightMeasurementWindow(
                defaultMeasurement().weight(startWeight).timestamp(startDate).build(),
                defaultMeasurement().weight(endWeight).timestamp(endDate).build()
        );
        BodyWeightProfile profile = new BodyWeightProfile(new User.UserId(1L), measurementWindow);

        assertThat(profile.isProgressMade()).isEqualTo(expectedProgress);
    }

    private static Stream<Arguments> provideInputAndResultForCalculateIsProgressMade() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(80L), BigDecimal.valueOf(90L), false),
                Arguments.of(BigDecimal.valueOf(90L), BigDecimal.valueOf(80L), true),
                Arguments.of(BigDecimal.valueOf(80L), BigDecimal.valueOf(80L), false)
        );
    }

}
