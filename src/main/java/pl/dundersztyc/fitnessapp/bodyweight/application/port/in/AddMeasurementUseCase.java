package pl.dundersztyc.fitnessapp.bodyweight.application.port.in;

import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;

public interface AddMeasurementUseCase {
    BodyWeightMeasurement addMeasurement(BodyWeightMeasurementRequest measurementRequest);
}
