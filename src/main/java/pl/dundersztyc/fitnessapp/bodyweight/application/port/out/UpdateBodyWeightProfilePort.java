package pl.dundersztyc.fitnessapp.bodyweight.application.port.out;

import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;

public interface UpdateBodyWeightProfilePort {
    void updateMeasurements(BodyWeightProfile profile);
}
