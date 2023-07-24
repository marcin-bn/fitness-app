package pl.dundersztyc.fitnessapp.bodyweight.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.AddMeasurementUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.CalculateProgressUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.LoadMeasurementsUseCase;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.LoadProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.UpdateBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProgress;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BodyWeightProfileService implements AddMeasurementUseCase, CalculateProgressUseCase, LoadMeasurementsUseCase {

    private final LoadProfilePort loadProfilePort;
    private final UpdateBodyWeightProfilePort updateProfilePort;

    @Override
    public BodyWeightMeasurement addMeasurement(BodyWeightMeasurementRequest measurementRequest) {
        var measurement = BodyWeightMeasurement.builder()
                .userId(new User.UserId(measurementRequest.userId()))
                .weight(measurementRequest.weight())
                .timestamp(LocalDateTime.now())
                .build();

        LocalDateTime baselineDate = LocalDateTime.now().minusWeeks(2);
        BodyWeightProfile profile = loadProfilePort.loadBodyWeightProfile(new User.UserId(measurementRequest.userId()), baselineDate);
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
            return loadProfilePort.loadBodyWeightProfile(new User.UserId(userId), startDate, finishDate.get());
        }
        return loadProfilePort.loadBodyWeightProfile(new User.UserId(userId), startDate);
    }
}
