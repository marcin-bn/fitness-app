package pl.dundersztyc.fitnessapp.stepcounter.application.port.in;

import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoadStepMeasurementsUseCase {
    List<StepMeasurement> loadMeasurements(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
    List<StepMeasurement> loadMeasurementsWithSpecifiedTypes(Long userId, List<StepMeasurementType> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
}
