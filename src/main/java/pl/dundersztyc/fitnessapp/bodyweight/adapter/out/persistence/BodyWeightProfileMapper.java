package pl.dundersztyc.fitnessapp.bodyweight.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurementWindow;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class BodyWeightProfileMapper {

    private final BodyWeightMeasurementMapper measurementMapper;

    public BodyWeightProfile mapToDomainEntity(User.UserId userId, List<BodyWeightMeasurementJpaEntity> measurements) {

        var mappedMeasurements = measurements.stream()
                .map(measurementMapper::mapToDomainEntity)
                .collect(Collectors.toList());

        return new BodyWeightProfile(userId, new BodyWeightMeasurementWindow(mappedMeasurements));
    }
}
