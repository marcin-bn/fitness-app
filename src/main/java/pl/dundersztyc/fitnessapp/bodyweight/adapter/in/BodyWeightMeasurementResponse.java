package pl.dundersztyc.fitnessapp.bodyweight.adapter.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BodyWeightMeasurementResponse(
        @NonNull Long userId,
        @NonNull LocalDateTime date,
        @NonNull BigDecimal weight) {

    public static BodyWeightMeasurementResponse of(BodyWeightMeasurement measurement) {
        return new BodyWeightMeasurementResponse(
                measurement.getUserId().value(),
                measurement.getTimestamp(),
                measurement.getWeight());
    }
}
