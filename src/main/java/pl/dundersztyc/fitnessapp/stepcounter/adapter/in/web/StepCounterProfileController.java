package pl.dundersztyc.fitnessapp.stepcounter.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dundersztyc.fitnessapp.stepcounter.adapter.in.StepMeasurementResponse;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.AddStepMeasurementUseCase;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.CalculateStepCounterProfileStatsUseCase;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.LoadStepMeasurementsUseCase;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.StepMeasurementRequest;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/step-counter/")
@RequiredArgsConstructor
class StepCounterProfileController {

    private final AddStepMeasurementUseCase addStepMeasurementUseCase;
    private final CalculateStepCounterProfileStatsUseCase calculateStepCounterProfileStatsUseCase;
    private final LoadStepMeasurementsUseCase loadStepMeasurementsUseCase;


    @GetMapping("users/{userId}/measurements")
    List<StepMeasurementResponse> getMeasurementHistory(@PathVariable("userId") Long userId,
                                                        @RequestParam("types") Optional<List<StepMeasurementType>> types,
                                                        @RequestParam("startDate") LocalDateTime startDate,
                                                        @RequestParam("finishDate") Optional<LocalDateTime> finishDate) {
        var measurements = getMeasurements(userId, types, startDate, finishDate);
        return measurements.stream()
                .map(StepMeasurementResponse::of)
                .collect(Collectors.toList());
    }

    @GetMapping("users/{userId}/measurements/steps/average")
    Long getAverageSteps(@PathVariable("userId") Long userId,
                         @RequestParam("types") Optional<List<StepMeasurementType>> types,
                         @RequestParam("startDate") LocalDateTime startDate,
                         @RequestParam("finishDate") Optional<LocalDateTime> finishDate) {
        return calculateAverageSteps(userId, types, startDate, finishDate);
    }

    @GetMapping("users/{userId}/measurements/steps/average-daily")
    Long getAverageDailySteps(@PathVariable("userId") Long userId,
                         @RequestParam("types") Optional<List<StepMeasurementType>> types,
                         @RequestParam("startDate") LocalDateTime startDate,
                         @RequestParam("finishDate") Optional<LocalDateTime> finishDate) {
        return calculateAverageDailySteps(userId, types, startDate, finishDate);
    }

    @PostMapping("measurements")
    StepMeasurementResponse addMeasurement(@RequestBody StepMeasurementRequest measurementRequest) {
        return StepMeasurementResponse.of(addStepMeasurementUseCase.addMeasurement(measurementRequest));
    }

    private List<StepMeasurement> getMeasurements(Long userId, Optional<List<StepMeasurementType>> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        if (types.isEmpty()) {
            return loadStepMeasurementsUseCase.loadMeasurements(userId, startDate, finishDate);
        }
        return loadStepMeasurementsUseCase.loadMeasurementsWithSpecifiedTypes(userId, types.get(), startDate, finishDate);
    }

    private Long calculateAverageSteps(Long userId, Optional<List<StepMeasurementType>> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        if (types.isEmpty()) {
            return calculateStepCounterProfileStatsUseCase.calculateAverageSteps(userId, startDate, finishDate);
        }
        return calculateStepCounterProfileStatsUseCase.calculateAverageStepsWithSpecifiedTypes(userId, types.get(), startDate, finishDate);
    }

    private Long calculateAverageDailySteps(Long userId, Optional<List<StepMeasurementType>> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        if (types.isEmpty()) {
            return calculateStepCounterProfileStatsUseCase.calculateAverageDailySteps(userId, startDate, finishDate);
        }
        return calculateStepCounterProfileStatsUseCase.calculateAverageDailyStepsWithSpecifiedTypes(userId, types.get(), startDate, finishDate);
    }

}
