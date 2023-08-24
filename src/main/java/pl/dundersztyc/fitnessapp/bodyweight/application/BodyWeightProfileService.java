package pl.dundersztyc.fitnessapp.bodyweight.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.AddBodyWeightMeasurementUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.CalculateBodyWeightProgressUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.LoadBodyWeightMeasurementsUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.LoadBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.UpdateBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurementWindow;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProgress;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
class BodyWeightProfileService implements AddBodyWeightMeasurementUseCase, CalculateBodyWeightProgressUseCase, LoadBodyWeightMeasurementsUseCase {

    private final LoadBodyWeightProfilePort loadBodyWeightProfilePort;
    private final UpdateBodyWeightProfilePort updateProfilePort;

    @Override
    public BodyWeightMeasurement addMeasurement(BodyWeightMeasurementRequest measurementRequest) {
        var measurement = BodyWeightMeasurement.builder()
                .userId(new User.UserId(measurementRequest.userId()))
                .weight(measurementRequest.weight())
                .timestamp(LocalDateTime.now())
                .build();

        BodyWeightProfile profile = new BodyWeightProfile(new User.UserId(measurementRequest.userId()), new BodyWeightMeasurementWindow());
        profile.addMeasurement(measurement);

        updateProfilePort.updateMeasurements(profile);

        return measurement;
    }

    @Override
    public BodyWeightProgress calculateProgress(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        BodyWeightProfile profile = loadProfile(userId, startDate, finishDate);
        return profile.getProgress();
    }


    @Override
    public List<BodyWeightMeasurement> loadMeasurements(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        BodyWeightProfile profile = loadProfile(userId, startDate, finishDate);
        return profile.getMeasurementWindow().getMeasurements();
    }

    private BodyWeightProfile loadProfile(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        if (finishDate.isPresent()) {
            return loadBodyWeightProfilePort.load(new User.UserId(userId), startDate, finishDate.get());
        }
        return loadBodyWeightProfilePort.load(new User.UserId(userId), startDate);
    }
}
