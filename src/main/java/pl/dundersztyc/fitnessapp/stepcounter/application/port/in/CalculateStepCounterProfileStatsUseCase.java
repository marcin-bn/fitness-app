package pl.dundersztyc.fitnessapp.stepcounter.application.port.in;

import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CalculateStepCounterProfileStatsUseCase {
    Long calculateAverageSteps(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
    Long calculateAverageStepsWithSpecifiedTypes(Long userId, List<StepMeasurementType> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
    Long calculateAverageDailySteps(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
    Long calculateAverageDailyStepsWithSpecifiedTypes(Long userId, List<StepMeasurementType> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
}
