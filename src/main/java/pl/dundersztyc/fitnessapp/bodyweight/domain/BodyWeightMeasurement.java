package pl.dundersztyc.fitnessapp.bodyweight.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
@Builder
public class BodyWeightMeasurement {

    private BodyWeightMeasurementId id;

    @NonNull
    private final User.UserId userId;

    @NonNull
    private final BigDecimal weight;

    @NonNull
    private final LocalDateTime timestamp;


    public record BodyWeightMeasurementId(@NonNull Long value) {
    }
}
