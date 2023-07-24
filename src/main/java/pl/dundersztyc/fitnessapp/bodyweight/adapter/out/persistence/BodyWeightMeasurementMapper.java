package pl.dundersztyc.fitnessapp.bodyweight.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.user.domain.User;

@Component
public class BodyWeightMeasurementMapper {

    public BodyWeightMeasurement mapToDomainEntity(BodyWeightMeasurementJpaEntity measurementJpaEntity) {
        return BodyWeightMeasurement.builder()
                .id(new BodyWeightMeasurement.BodyWeightMeasurementId(measurementJpaEntity.getId()))
                .userId(new User.UserId(measurementJpaEntity.getUserId()))
                .weight(measurementJpaEntity.getWeight())
                .timestamp(measurementJpaEntity.getTimestamp())
                .build();
    }

    public BodyWeightMeasurementJpaEntity mapToJpaEntity(BodyWeightMeasurement measurement) {
        return BodyWeightMeasurementJpaEntity.builder()
                .id(measurement.getId() == null ? null : measurement.getId().value())
                .userId(measurement.getUserId().value())
                .weight(measurement.getWeight())
                .timestamp(measurement.getTimestamp())
                .build();
    }
}
