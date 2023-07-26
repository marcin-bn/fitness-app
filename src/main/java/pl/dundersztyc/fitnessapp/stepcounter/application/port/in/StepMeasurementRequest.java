package pl.dundersztyc.fitnessapp.stepcounter.application.port.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;

public record StepMeasurementRequest(@NonNull Long userId, @NonNull Long steps, @NonNull StepMeasurementType type, @NonNull LocalDateTime timestamp) {
}
