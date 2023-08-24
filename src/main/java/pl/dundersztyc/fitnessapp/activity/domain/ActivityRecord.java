package pl.dundersztyc.fitnessapp.activity.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;

import java.time.LocalDateTime;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record ActivityRecord(
        Long id,
        Coordinates coordinates,
        @Positive Long heartRate,
        @NotNull LocalDateTime timestamp) {

    public ActivityRecord(Long id,
                          Coordinates coordinates,
                          Long heartRate,
                          LocalDateTime timestamp) {
        this.id = id;
        this.coordinates = coordinates;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
        validate(this);
    }

    public static ActivityRecord withoutId(Coordinates coordinates,
                                        Long heartRate,
                                        LocalDateTime timestamp) {
        return new ActivityRecord(null, coordinates, heartRate, timestamp);
    }
}
