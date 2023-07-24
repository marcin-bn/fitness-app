package pl.dundersztyc.fitnessapp.bodyweight.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dundersztyc.fitnessapp.bodyweight.adapter.in.BodyWeightMeasurementResponse;
import pl.dundersztyc.fitnessapp.bodyweight.adapter.in.BodyWeightProgressResponse;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.AddBodyWeightMeasurementUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.CalculateBodyWeightProgressUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.LoadBodyWeightMeasurementsUseCase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/body-weight/")
@RequiredArgsConstructor
class BodyWeightProfileController {

    private final AddBodyWeightMeasurementUseCase addBodyWeightMeasurementUseCase;
    private final CalculateBodyWeightProgressUseCase calculateBodyWeightProgressUseCase;
    private final LoadBodyWeightMeasurementsUseCase loadBodyWeightMeasurementsUseCase;


    @GetMapping("measurements/progress/{userId}")
    BodyWeightProgressResponse getBodyWeightProgress(@PathVariable("userId") Long userId,
                                                     @RequestParam("startDate") LocalDateTime startDate,
                                                     @RequestParam("finishDate") Optional<LocalDateTime> finishDate) {
        return BodyWeightProgressResponse.of(calculateBodyWeightProgressUseCase.calculateProgress(userId, startDate, finishDate));
    }

    @GetMapping("measurements/users/{userId}")
    List<BodyWeightMeasurementResponse> getMeasurementHistory(@PathVariable("userId") Long userId,
                                                              @RequestParam("startDate") LocalDateTime startDate,
                                                              @RequestParam("finishDate") Optional<LocalDateTime> finishDate) {
        var measurements = loadBodyWeightMeasurementsUseCase.loadMeasurements(userId, startDate, finishDate);
        return measurements.stream()
                .map(BodyWeightMeasurementResponse::of)
                .collect(Collectors.toList());
    }

    @PostMapping("measurements")
    BodyWeightMeasurementResponse addMeasurement(@RequestBody BodyWeightMeasurementRequest measurementRequest) {
        return BodyWeightMeasurementResponse.of(addBodyWeightMeasurementUseCase.addMeasurement(measurementRequest));
    }

}
