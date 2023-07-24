package pl.dundersztyc.fitnessapp.stepcounter.application.port.out;

import pl.dundersztyc.fitnessapp.stepcounter.domain.StepCounterProfile;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface LoadStepCounterProfilePort {
    StepCounterProfile load(User.UserId userId, LocalDateTime baselineDate);
    StepCounterProfile load(User.UserId userId, LocalDateTime baselineDate, LocalDateTime finishDate);
    StepCounterProfile loadWithMeasurementTypes(User.UserId userId, List<StepMeasurementType> measurementTypes, LocalDateTime baselineDate);
    StepCounterProfile loadWithMeasurementTypes(User.UserId userId, List<StepMeasurementType> measurementTypes, LocalDateTime baselineDate, LocalDateTime finishDate);
}
