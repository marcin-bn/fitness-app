package pl.dundersztyc.fitnessapp.stepcounter.adapter.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;

public record StepMeasurementResponse(
        @NonNull Long userId,
        @NonNull LocalDateTime date,
        @NonNull Long steps,
        @NonNull StepMeasurementType type) {

    public static StepMeasurementResponse of(StepMeasurement measurement) {
        return new StepMeasurementResponse(
                measurement.getUserId().value(),
                measurement.getTimestamp(),
                measurement.getSteps(),
                measurement.getType());
    }
}
