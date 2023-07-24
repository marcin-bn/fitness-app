package pl.dundersztyc.fitnessapp.bodyweight.application.port.in;

import lombok.NonNull;

import java.math.BigDecimal;

public record BodyWeightMeasurementRequest(@NonNull Long userId, @NonNull BigDecimal weight) {
}
