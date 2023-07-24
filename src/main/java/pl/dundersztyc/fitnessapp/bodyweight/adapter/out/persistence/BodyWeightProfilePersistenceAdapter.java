package pl.dundersztyc.fitnessapp.bodyweight.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.LoadBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.UpdateBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class BodyWeightProfilePersistenceAdapter implements LoadBodyWeightProfilePort, UpdateBodyWeightProfilePort {

    private final BodyWeightMeasurementRepository measurementRepository;
    private final BodyWeightProfileMapper profileMapper;
    private final BodyWeightMeasurementMapper measurementMapper;

    @Override
    public BodyWeightProfile load(User.UserId userId, LocalDateTime baselineDate) {
        var measurements = measurementRepository.findByUserSince(userId.value(), baselineDate);
        return profileMapper.mapToDomainEntity(userId, measurements);
    }

    @Override
    public BodyWeightProfile load(User.UserId userId, LocalDateTime baselineDate, LocalDateTime finishDate) {
        var measurements = measurementRepository.findByUserFromTo(userId.value(), baselineDate, finishDate);
        return profileMapper.mapToDomainEntity(userId, measurements);
    }

    @Override
    public void updateMeasurements(BodyWeightProfile profile) {
        profile.getMeasurementWindow().getMeasurements().stream()
                .filter(measurement -> measurement.getId() == null)
                .map(measurementMapper::mapToJpaEntity)
                .forEach(measurementRepository::save);
    }
}
