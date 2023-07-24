package pl.dundersztyc.fitnessapp.stepcounter.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
@Builder
public class StepMeasurement {

    private StepMeasurementId id;

    @NonNull
    private final User.UserId userId;

    @NonNull
    private final Long steps;

    @NonNull
    private final StepMeasurementType type;

    @NonNull
    private final LocalDateTime timestamp;

    public record StepMeasurementId(@NonNull Long value) {
    }
}
