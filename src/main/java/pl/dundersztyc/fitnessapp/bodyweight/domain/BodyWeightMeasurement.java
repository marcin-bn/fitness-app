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

    private final User.UserId userId;
    private final BigDecimal weight;
    private final LocalDateTime timestamp;


    public record BodyWeightMeasurementId(@NonNull Long value) {
    }
}
