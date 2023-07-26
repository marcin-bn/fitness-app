package pl.dundersztyc.fitnessapp.stepcounter.application.port.in;

import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;

public interface AddStepMeasurementUseCase {
    StepMeasurement addMeasurement(StepMeasurementRequest measurementRequest);
}
