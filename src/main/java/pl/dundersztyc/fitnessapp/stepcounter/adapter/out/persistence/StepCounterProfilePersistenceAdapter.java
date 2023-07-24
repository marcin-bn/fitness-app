package pl.dundersztyc.fitnessapp.stepcounter.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.out.LoadStepCounterProfilePort;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.out.UpdateStepCounterProfilePort;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepCounterProfile;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
class StepCounterProfilePersistenceAdapter implements LoadStepCounterProfilePort, UpdateStepCounterProfilePort {

    private final StepMeasurementRepository measurementRepository;
    private final StepCounterProfileMapper profileMapper;
    private final StepMeasurementMapper measurementMapper;

    @Override
    public StepCounterProfile load(User.UserId userId, LocalDateTime baselineDate) {
        var measurements = measurementRepository.findByUserSince(userId.value(), baselineDate);
        return profileMapper.mapToDomainEntity(userId, measurements);
    }

    @Override
    public StepCounterProfile load(User.UserId userId, LocalDateTime baselineDate, LocalDateTime finishDate) {
        var measurements = measurementRepository.findByUserFromTo(userId.value(), baselineDate, finishDate);
        return profileMapper.mapToDomainEntity(userId, measurements);
    }

    @Override
    public StepCounterProfile loadWithMeasurementTypes(User.UserId userId, List<StepMeasurementType> measurementTypes, LocalDateTime baselineDate) {
        var measurements = measurementRepository.findByUserSinceWithTypes(userId.value(), baselineDate, measurementTypes);
        return profileMapper.mapToDomainEntity(userId, measurements);
    }

    @Override
    public StepCounterProfile loadWithMeasurementTypes(User.UserId userId, List<StepMeasurementType> measurementTypes, LocalDateTime baselineDate, LocalDateTime finishDate) {
        var measurements = measurementRepository.findByUserFromToWithTypes(userId.value(), baselineDate, finishDate, measurementTypes);
        return profileMapper.mapToDomainEntity(userId, measurements);
    }

    @Override
    public void updateMeasurements(StepCounterProfile profile) {
        profile.getMeasurementWindow().getMeasurements().stream()
                .filter(measurement -> measurement.getId() == null)
                .map(measurementMapper::mapToJpaEntity)
                .forEach(measurementRepository::save);
    }
}
