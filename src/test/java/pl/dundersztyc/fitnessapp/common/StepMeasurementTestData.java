package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;

public class StepMeasurementTestData {

    public static StepMeasurement.StepMeasurementBuilder defaultStepMeasurement() {
        return StepMeasurement.builder()
                .userId(new User.UserId(1L))
                .steps(8000L)
                .type(StepMeasurementType.DAILY_ACTIVITY)
                .timestamp(LocalDateTime.of(2019, 8, 3, 0, 0));
    }
}
