package pl.dundersztyc.fitnessapp.stepcounter.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record StepMeasurementRequest(@NotNull Long userId, @PositiveOrZero @NotNull Long steps, @NotNull StepMeasurementType type, @NotNull LocalDateTime timestamp) {

    public StepMeasurementRequest(
            Long userId,
            Long steps,
            StepMeasurementType type,
            LocalDateTime timestamp) {
        this.userId = userId;
        this.steps = steps;
        this.type = type;
        this.timestamp = timestamp;
        validate(this);
    }
}
