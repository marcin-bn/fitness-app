package pl.dundersztyc.fitnessapp.bodyweight.application.port.in;

import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoadBodyWeightMeasurementsUseCase {
    List<BodyWeightMeasurement> loadMeasurements(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
}
