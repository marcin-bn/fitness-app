package pl.dundersztyc.fitnessapp.stepcounter.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.AddStepMeasurementUseCase;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.CalculateStepCounterProfileStatsUseCase;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.LoadStepMeasurementsUseCase;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.StepMeasurementRequest;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.out.LoadStepCounterProfilePort;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.out.UpdateStepCounterProfilePort;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepCounterProfile;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepCounterProfileService implements AddStepMeasurementUseCase, LoadStepMeasurementsUseCase, CalculateStepCounterProfileStatsUseCase {

    private final LoadStepCounterProfilePort loadStepCounterProfilePort;
    private final UpdateStepCounterProfilePort updateStepCounterProfilePort;

    @Override
    public StepMeasurement addMeasurement(StepMeasurementRequest measurementRequest) {
        var measurement = StepMeasurement.builder()
                .userId(new User.UserId(measurementRequest.userId()))
                .steps(measurementRequest.steps())
                .type(measurementRequest.type())
                .timestamp(measurementRequest.timestamp())
                .build();

        StepCounterProfile profile = new StepCounterProfile(new User.UserId(measurementRequest.userId()), new StepMeasurementWindow());
        profile.addMeasurement(measurement);

        updateStepCounterProfilePort.updateMeasurements(profile);
        return measurement;
    }

    @Override
    public Long calculateAverageSteps(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        var profile = loadProfile(userId, startDate, finishDate);
        return profile.getAverageSteps();
    }

    @Override
    public Long calculateAverageStepsWithSpecifiedTypes(Long userId, List<StepMeasurementType> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        var profile = loadProfileWithSpecifiedTypes(userId, types, startDate, finishDate);
        return profile.getAverageSteps();
    }

    @Override
    public Long calculateAverageDailySteps(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        var profile = loadProfile(userId, startDate, finishDate);
        return profile.getAverageDailySteps();
    }

    @Override
    public Long calculateAverageDailyStepsWithSpecifiedTypes(Long userId, List<StepMeasurementType> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        var profile = loadProfileWithSpecifiedTypes(userId, types, startDate, finishDate);
        return profile.getAverageDailySteps();
    }

    @Override
    public List<StepMeasurement> loadMeasurements(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        var profile = loadProfile(userId, startDate, finishDate);
        return profile.getMeasurementWindow().getMeasurements();
    }

    @Override
    public List<StepMeasurement> loadMeasurementsWithSpecifiedTypes(Long userId, List<StepMeasurementType> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        var profile = loadProfileWithSpecifiedTypes(userId, types, startDate, finishDate);
        return profile.getMeasurementWindow().getMeasurements();
    }

    private StepCounterProfile loadProfile(Long userId, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        if (finishDate.isPresent()) {
            return loadStepCounterProfilePort.load(new User.UserId(userId), startDate, finishDate.get());
        }
        return loadStepCounterProfilePort.load(new User.UserId(userId), startDate);
    }

    private StepCounterProfile loadProfileWithSpecifiedTypes(Long userId, List<StepMeasurementType> types, LocalDateTime startDate, Optional<LocalDateTime> finishDate) {
        if (finishDate.isPresent()) {
            return loadStepCounterProfilePort.loadWithSpecifiedMeasurementTypes(new User.UserId(userId), types, startDate, finishDate.get());
        }
        return loadStepCounterProfilePort.loadWithSpecifiedMeasurementTypes(new User.UserId(userId), types, startDate);
    }
}
