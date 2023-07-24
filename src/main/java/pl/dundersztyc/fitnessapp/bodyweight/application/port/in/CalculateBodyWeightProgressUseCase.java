package pl.dundersztyc.fitnessapp.bodyweight.application.port.in;

import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProgress;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CalculateBodyWeightProgressUseCase {
    BodyWeightProgress calculateProgress(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate);
}
