package pl.dundersztyc.fitnessapp.stepcounter.domain;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.*;

public class StepMeasurementWindow {

    private List<StepMeasurement> measurements;

    public StepMeasurementWindow(@NonNull List<StepMeasurement> measurements) {
        this.measurements = measurements;
    }

    public StepMeasurementWindow(@NonNull StepMeasurement... measurements) {
        this.measurements = new ArrayList<>(Arrays.asList(measurements));
    }

    public void addMeasurement(StepMeasurement measurement) {
        this.measurements.add(measurement);
    }

    public List<StepMeasurement> getMeasurements() {
        return Collections.unmodifiableList(measurements);
    }

    public LocalDateTime getStartTimestamp() {
        return getFirstMeasurement().getTimestamp();
    }

    public LocalDateTime getEndTimestamp() {
        return getLastMeasurement().getTimestamp();
    }

    public Long getMinSteps() {
        return getMin(Comparator.comparing(StepMeasurement::getSteps)).getSteps();
    }

    public Long getMaxSteps() {
        return getMax(Comparator.comparing(StepMeasurement::getSteps)).getSteps();
    }

    public Long getAverageSteps() {
        return Double.valueOf(
                measurements.stream()
                    .mapToLong(StepMeasurement::getSteps)
                    .average()
                    .orElseThrow(IllegalStateException::new)
        ).longValue();
    }

    private StepMeasurement getFirstMeasurement() {
        return getMin(Comparator.comparing(StepMeasurement::getTimestamp));
    }

    private StepMeasurement getLastMeasurement() {
        return getMax(Comparator.comparing(StepMeasurement::getTimestamp));
    }

    private StepMeasurement getMin(Comparator<StepMeasurement> comparator) {
        return measurements.stream()
                .min(comparator)
                .orElseThrow(IllegalStateException::new);
    }

    private StepMeasurement getMax(Comparator<StepMeasurement> comparator) {
        return measurements.stream()
                .max(comparator)
                .orElseThrow(IllegalStateException::new);
    }

}
