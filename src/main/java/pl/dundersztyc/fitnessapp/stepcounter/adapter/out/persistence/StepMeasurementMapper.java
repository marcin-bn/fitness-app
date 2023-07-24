package pl.dundersztyc.fitnessapp.stepcounter.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.user.domain.User;

@Component
class StepMeasurementMapper {

    public StepMeasurement mapToDomainEntity(StepMeasurementJpaEntity measurementJpaEntity) {
        return StepMeasurement.builder()
                .id(new StepMeasurement.StepMeasurementId(measurementJpaEntity.getId()))
                .userId(new User.UserId(measurementJpaEntity.getUserId()))
                .steps(measurementJpaEntity.getSteps())
                .type(measurementJpaEntity.getType())
                .timestamp(measurementJpaEntity.getTimestamp())
                .build();
    }

    public StepMeasurementJpaEntity mapToJpaEntity(StepMeasurement measurement) {
        return StepMeasurementJpaEntity.builder()
                .id(measurement.getId() == null ? null : measurement.getId().value())
                .userId(measurement.getUserId().value())
                .steps(measurement.getSteps())
                .type(measurement.getType())
                .timestamp(measurement.getTimestamp())
                .build();
    }
}
