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
        return measurements.stream()
                .min(Comparator.comparing(BodyWeightMeasurement::getWeight))
                .orElseThrow(IllegalStateException::new)
                .getWeight();
    }

    public BigDecimal getMaxWeight() {
        return measurements.stream()
                .max(Comparator.comparing(BodyWeightMeasurement::getWeight))
                .orElseThrow(IllegalStateException::new)
                .getWeight();
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
        return measurements.stream()
                .min(Comparator.comparing(BodyWeightMeasurement::getTimestamp))
                .orElseThrow(IllegalStateException::new);
    }

    private BodyWeightMeasurement getLastMeasurement() {
        return measurements.stream()
                .max(Comparator.comparing(BodyWeightMeasurement::getTimestamp))
                .orElseThrow(IllegalStateException::new);
    }

}
