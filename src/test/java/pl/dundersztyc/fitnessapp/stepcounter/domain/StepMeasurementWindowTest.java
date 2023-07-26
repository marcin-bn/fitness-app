package pl.dundersztyc.fitnessapp.stepcounter.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.StepMeasurementTestData.defaultStepMeasurement;

public class StepMeasurementWindowTest {

    @Test
    void getStartTimestamp() {
        var measurementWindow = new StepMeasurementWindow(
                defaultStepMeasurement().timestamp(startDate()).build(),
                defaultStepMeasurement().timestamp(inBetweenDate()).build(),
                defaultStepMeasurement().timestamp(endDate()).build()
        );

        assertThat(measurementWindow.getStartTimestamp()).isEqualTo(startDate());
    }

    @Test
    void getEndTimestamp() {
        var measurementWindow = new StepMeasurementWindow(
                defaultStepMeasurement().timestamp(startDate()).build(),
                defaultStepMeasurement().timestamp(inBetweenDate()).build(),
                defaultStepMeasurement().timestamp(endDate()).build()
        );

        assertThat(measurementWindow.getEndTimestamp()).isEqualTo(endDate());
    }

    @Test
    void getMinSteps() {
        var measurementWindow = new StepMeasurementWindow(
                defaultStepMeasurement().steps(9000L).build(),
                defaultStepMeasurement().steps(10000L).build(),
                defaultStepMeasurement().steps(8000L).build()
        );

        assertThat(measurementWindow.getMinSteps()).isEqualTo(8000L);
    }

    @Test
    void getMaxSteps() {
        var measurementWindow = new StepMeasurementWindow(
                defaultStepMeasurement().steps(9000L).build(),
                defaultStepMeasurement().steps(10000L).build(),
                defaultStepMeasurement().steps(8000L).build()
        );

        assertThat(measurementWindow.getMaxSteps()).isEqualTo(10000L);
    }

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateAverageSteps")
    void calculateAverageSteps(List<Long> stepMeasurements, Long expectedAverageSteps) {
        var measurementWindow = prepareWindow(stepMeasurements);

        assertThat(measurementWindow.getAverageSteps()).isEqualTo(expectedAverageSteps);
    }

    private static Stream<Arguments> provideInputAndResultForCalculateAverageSteps() {
        return Stream.of(
                Arguments.of(List.of(8000L, 9000L, 10000L), 9000L),
                Arguments.of(List.of(0L, 0L, 0L), 0L),
                Arguments.of(List.of(5L, 6L), 5L),
                Arguments.of(List.of(1L, 2L, 2L), 1L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputAndResultForCalculateAverageDailySteps")
    void calculateAverageDailySteps(LocalDateTime startDate, LocalDateTime finishDate, LocalDateTime inBetweenDate, List<Long> stepMeasurements, Long expectedAverageDailySteps) {
        var measurementWindow = prepareWindow(startDate, finishDate, inBetweenDate, stepMeasurements);

        assertThat(measurementWindow.getAverageDailySteps()).isEqualTo(expectedAverageDailySteps);
    }

    private static Stream<Arguments> provideInputAndResultForCalculateAverageDailySteps() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime finishDate = LocalDateTime.of(2020, 1, 11, 1, 1);
        LocalDateTime inBetweenDate = LocalDateTime.of(2020, 1, 5, 1, 1);

        return Stream.of(
                Arguments.of(startDate, finishDate, inBetweenDate, List.of(8000L, 9000L, 10000L), 2700L),
                Arguments.of(startDate, finishDate, inBetweenDate, List.of(0L, 0L, 0L), 0L),
                Arguments.of(startDate, finishDate, inBetweenDate, List.of(1L, 10L), 1L),
                Arguments.of(startDate, finishDate, inBetweenDate, List.of(9L, 10L), 1L)
        );
    }

    private StepMeasurementWindow prepareWindow(List<Long> stepMeasurements) {
        var measurements = stepMeasurements.stream()
                .map(steps -> defaultStepMeasurement().steps(steps).build())
                .toList();
        return new StepMeasurementWindow(measurements);
    }

    private StepMeasurementWindow prepareWindow(LocalDateTime startDate, LocalDateTime finishDate, LocalDateTime inBetweenDate, List<Long> stepMeasurements) {
        assertThat(stepMeasurements).hasSizeGreaterThanOrEqualTo(2);

        var measurements = stepMeasurements.stream()
                .map(steps -> defaultStepMeasurement().steps(steps).timestamp(inBetweenDate).build())
                .collect(Collectors.toList());

        // set first measurement
        measurements.set(0, defaultStepMeasurement()
                .steps(stepMeasurements.get(0))
                .timestamp(startDate)
                .build());

        // set last measurement
        measurements.set(measurements.size() - 1, defaultStepMeasurement()
                .steps(stepMeasurements.get(measurements.size() - 1))
                .timestamp(finishDate)
                .build());

        return new StepMeasurementWindow(measurements);
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
