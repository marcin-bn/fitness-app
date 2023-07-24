package pl.dundersztyc.fitnessapp.bodyweight.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class BodyWeightProfile {

    private final User.UserId userId;

    @Getter
    private final BodyWeightMeasurementWindow measurementWindow;

    public void addMeasurement(BodyWeightMeasurement measurement) {
        measurementWindow.addMeasurement(measurement);
    }

    public BigDecimal getMinWeight() {
        return measurementWindow.getMinWeight();
    }

    public BigDecimal getMaxWeight() {
        return measurementWindow.getMaxWeight();
    }

    public BodyWeightProgress getProgress() {
        return measurementWindow.getProgress();
    }

    public boolean isProgressMade() {
        final BigDecimal weightLoss = getProgress().getWeightLoss();
        return weightLoss.compareTo(BigDecimal.ZERO) > 0;
    }

}
