package pl.dundersztyc.fitnessapp.bodyweight.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.BodyWeightMeasurementTestData.defaultBodyWeightMeasurement;

class BodyWeightMeasurementWindowTest {

    @Test
    void getStartTimestamp() {
        var measurementWindow = new BodyWeightMeasurementWindow(
                defaultBodyWeightMeasurement().timestamp(startDate()).build(),
                defaultBodyWeightMeasurement().timestamp(inBetweenDate()).build(),
                defaultBodyWeightMeasurement().timestamp(endDate()).build()
        );

        assertThat(measurementWindow.getStartTimestamp()).isEqualTo(startDate());
    }

    @Test
    void getEndTimestamp() {
        var measurementWindow = new BodyWeightMeasurementWindow(
                defaultBodyWeightMeasurement().timestamp(startDate()).build(),
                defaultBodyWeightMeasurement().timestamp(inBetweenDate()).build(),
                defaultBodyWeightMeasurement().timestamp(endDate()).build()
        );

        assertThat(measurementWindow.getEndTimestamp()).isEqualTo(endDate());
    }

    @Test
    void getMinWeight() {
        var measurementWindow = new BodyWeightMeasurementWindow(
                defaultBodyWeightMeasurement().weight(BigDecimal.valueOf(90L)).build(),
                defaultBodyWeightMeasurement().weight(BigDecimal.valueOf(50L)).build(),
                defaultBodyWeightMeasurement().weight(BigDecimal.valueOf(70L)).build()
        );

        assertThat(measurementWindow.getMinWeight()).isEqualTo(BigDecimal.valueOf(50L));
    }

    @Test
    void getMaxWeight() {
        var measurementWindow = new BodyWeightMeasurementWindow(
                defaultBodyWeightMeasurement().weight(BigDecimal.valueOf(90L)).build(),
                defaultBodyWeightMeasurement().weight(BigDecimal.valueOf(50L)).build(),
                defaultBodyWeightMeasurement().weight(BigDecimal.valueOf(70L)).build()
        );

        assertThat(measurementWindow.getMaxWeight()).isEqualTo(BigDecimal.valueOf(90L));
    }

    @ParameterizedTest
    @MethodSource("provideInputAndResultsForCalculateBodyWeightProgress")
    void calculateBodyWeightProgress(BigDecimal weightLoss, long weeks, BigDecimal expectedWeeklyLoss) {

        var measurementWindow = prepareWindow(weightLoss, weeks);
        BodyWeightProgress progress = measurementWindow.getProgress();

        assertThat(progress.getWeightLoss()).isEqualTo(weightLoss);
        assertThat(progress.getWeeklyWeightLoss().compareTo(expectedWeeklyLoss)).isEqualTo(0);
    }

    private static Stream<Arguments> provideInputAndResultsForCalculateBodyWeightProgress() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(40L), 4, BigDecimal.valueOf(10L)),
                Arguments.of(BigDecimal.valueOf(-40L), 4, BigDecimal.valueOf(-10L)),
                Arguments.of(BigDecimal.valueOf(0L), 0, BigDecimal.valueOf(0L)),
                Arguments.of(BigDecimal.valueOf(10L), 20, BigDecimal.valueOf(0.5))
        );
    }

    private BodyWeightMeasurementWindow prepareWindow(BigDecimal weightLoss, long weeks) {
        LocalDateTime startDate = startDate();
        BigDecimal startWeight = BigDecimal.valueOf(90L);

        LocalDateTime endDate = startDate.plusWeeks(weeks);
        BigDecimal endWeight = startWeight.subtract(weightLoss);

        return new BodyWeightMeasurementWindow(
                defaultBodyWeightMeasurement().timestamp(startDate).weight(startWeight).build(),
                defaultBodyWeightMeasurement().timestamp(endDate).weight(endWeight).build()
        );
    }

    private LocalDateTime startDate() {
        return LocalDateTime.of(2020, 1, 1, 0, 0);
    }

    private LocalDateTime inBetweenDate() {
        return LocalDateTime.of(2020, 1, 5, 0, 0);
    }

    private LocalDateTime endDate() {
        return LocalDateTime.of(2020, 1, 15, 0, 0);
    }
}