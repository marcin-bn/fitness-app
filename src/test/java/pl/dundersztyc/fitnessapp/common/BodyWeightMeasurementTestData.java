package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement.BodyWeightMeasurementBuilder;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BodyWeightMeasurementTestData {

    public static BodyWeightMeasurementBuilder defaultBodyWeightMeasurement() {
        return BodyWeightMeasurement.builder()
                .userId(new User.UserId(1L))
                .weight(BigDecimal.valueOf(85.5))
                .timestamp(LocalDateTime.of(2019, 8, 3, 0, 0));
    }

}
