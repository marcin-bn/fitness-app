package pl.dundersztyc.fitnessapp.activity.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;

import java.time.LocalDateTime;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record ActivityRecord(
    Coordinates coordinates,
    @Positive Long heartRate,
    @NotNull LocalDateTime timestamp) {

    public ActivityRecord(Coordinates coordinates,
                          Long heartRate,
                          LocalDateTime timestamp) {
        this.coordinates = coordinates;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
        validate(this);
    }
}
