package pl.dundersztyc.fitnessapp.stepcounter.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepCounterProfile;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class StepCounterProfileMapper {

    private final StepMeasurementMapper measurementMapper;

    public StepCounterProfile mapToDomainEntity(User.UserId userId, List<StepMeasurementJpaEntity> measurements) {

        var mappedMeasurements = measurements.stream()
                .map(measurementMapper::mapToDomainEntity)
                .collect(Collectors.toList());

        return new StepCounterProfile(userId, new StepMeasurementWindow(mappedMeasurements));
    }
}
