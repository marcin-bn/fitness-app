package pl.dundersztyc.fitnessapp.bodyweight.application.port.in;

import jakarta.validation.constraints.Positive;
import lombok.NonNull;

import java.math.BigDecimal;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record BodyWeightMeasurementRequest(@NonNull Long userId, @Positive @NonNull BigDecimal weight) {

    public BodyWeightMeasurementRequest(
            Long userId,
            BigDecimal weight) {
        this.userId = userId;
        this.weight = weight;
        validate(this);
    }

}
