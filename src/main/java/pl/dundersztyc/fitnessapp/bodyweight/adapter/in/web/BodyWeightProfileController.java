package pl.dundersztyc.fitnessapp.bodyweight.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dundersztyc.fitnessapp.bodyweight.adapter.in.BodyWeightMeasurementResponse;
import pl.dundersztyc.fitnessapp.bodyweight.adapter.in.BodyWeightProgressResponse;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.AddMeasurementUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.CalculateProgressUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.LoadMeasurementsUseCase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/body-weight/")
@RequiredArgsConstructor
class BodyWeightProfileController {

    private final AddMeasurementUseCase addMeasurementUseCase;
    private final CalculateProgressUseCase calculateProgressUseCase;
    private final LoadMeasurementsUseCase loadMeasurementsUseCase;


    @GetMapping("measurements/progress/{userId}")
    BodyWeightProgressResponse getBodyWeightProgress(@PathVariable("userId") Long userId,
                                                     @RequestParam("startDate") LocalDateTime startDate,
                                                     @RequestParam("finishDate") Optional<LocalDateTime> finishDate) {
        return BodyWeightProgressResponse.of(calculateProgressUseCase.calculateProgress(userId, startDate, finishDate));
    }

    @GetMapping("measurements/users/{userId}")
    List<BodyWeightMeasurementResponse> getMeasurementHistory(@PathVariable("userId") Long userId,
                                                              @RequestParam("startDate") LocalDateTime startDate,
                                                              @RequestParam("finishDate") Optional<LocalDateTime> finishDate) {
        var measurements = loadMeasurementsUseCase.loadMeasurements(userId, startDate, finishDate);
        return measurements.stream()
                .map(BodyWeightMeasurementResponse::of)
                .collect(Collectors.toList());
    }

    @PostMapping("measurements")
    BodyWeightMeasurementResponse addMeasurement(@RequestBody BodyWeightMeasurementRequest measurementRequest) {
        return BodyWeightMeasurementResponse.of(addMeasurementUseCase.addMeasurement(measurementRequest));
    }

}
