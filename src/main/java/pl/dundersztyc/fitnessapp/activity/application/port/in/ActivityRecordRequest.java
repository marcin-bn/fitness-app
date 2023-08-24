package pl.dundersztyc.fitnessapp.activity.application.port.in;

import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record ActivityRecordRequest(
        @Range(min = -90, max = 90) Double latitude,
        @Range(min = -180, max = 180) Double longitude,
        Long heartRate,
        @NonNull LocalDateTime timestamp
) {
    public ActivityRecordRequest(
            Double latitude,
            Double longitude,
            Long heartRate,
            LocalDateTime timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
        validate(this);
    }
}
