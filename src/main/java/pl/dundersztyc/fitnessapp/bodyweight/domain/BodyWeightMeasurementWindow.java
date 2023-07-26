package pl.dundersztyc.fitnessapp.bodyweight.domain;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BodyWeightMeasurementWindow {

    private List<BodyWeightMeasurement> measurements;

    public BodyWeightMeasurementWindow(@NonNull List<BodyWeightMeasurement> measurements) {
        this.measurements = measurements;
    }

    public BodyWeightMeasurementWindow(@NonNull BodyWeightMeasurement... measurements) {
        this.measurements = new ArrayList<>(Arrays.asList(measurements));
    }

    public LocalDateTime getStartTimestamp() {
        return getFirstMeasurement().getTimestamp();
    }

    public LocalDateTime getEndTimestamp() {
        return getLastMeasurement().getTimestamp();
    }

    public BigDecimal getMinWeight() {
        return getMin(Comparator.comparing(BodyWeightMeasurement::getWeight)).getWeight();
    }

    public BigDecimal getMaxWeight() {
        return getMax(Comparator.comparing(BodyWeightMeasurement::getWeight)).getWeight();
    }

    public void addMeasurement(BodyWeightMeasurement measurement) {
        this.measurements.add(measurement);
    }

    public List<BodyWeightMeasurement> getMeasurements() {
        return Collections.unmodifiableList(measurements);
    }

    public BodyWeightProgress getProgress() {
        BigDecimal initialBodyWeight = getFirstMeasurement().getWeight();
        BigDecimal finalBodyWeight = getLastMeasurement().getWeight();

        long weeks = getWeeksBetween(getStartTimestamp(), getEndTimestamp());
        BigDecimal weightLoss = initialBodyWeight.subtract(finalBodyWeight);

        return new BodyWeightProgress(weightLoss, weeks);
    }

    private long getWeeksBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.WEEKS.between(start, end);
    }

    private BodyWeightMeasurement getFirstMeasurement() {
        return getMin(Comparator.comparing(BodyWeightMeasurement::getTimestamp));
    }

    private BodyWeightMeasurement getLastMeasurement() {
        return getMax(Comparator.comparing(BodyWeightMeasurement::getTimestamp));
    }

    private BodyWeightMeasurement getMin(Comparator<BodyWeightMeasurement> comparator) {
        return measurements.stream()
                .min(comparator)
                .orElseThrow(IllegalStateException::new);
    }

    private BodyWeightMeasurement getMax(Comparator<BodyWeightMeasurement> comparator) {
        return measurements.stream()
                .max(comparator)
                .orElseThrow(IllegalStateException::new);
    }

}
