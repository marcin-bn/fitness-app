package pl.dundersztyc.fitnessapp.stepcounter.application.port.out;

import pl.dundersztyc.fitnessapp.stepcounter.domain.StepCounterProfile;

public interface UpdateStepCounterProfilePort {
    void updateMeasurements(StepCounterProfile profile);
}
