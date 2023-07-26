package pl.dundersztyc.fitnessapp.stepcounter.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.dundersztyc.fitnessapp.user.domain.User;

@RequiredArgsConstructor
public class StepCounterProfile {

    private final User.UserId userId;

    @Getter
    private final StepMeasurementWindow measurementWindow;

    public void addMeasurement(StepMeasurement measurement) {
        measurementWindow.addMeasurement(measurement);
    }

    public Long getMinSteps() {
        return measurementWindow.getMinSteps();
    }

    public Long getMaxSteps() {
        return measurementWindow.getMaxSteps();
    }

    public Long getAverageSteps() {
        return measurementWindow.getAverageSteps();
    }

    public Long getAverageDailySteps() {
        return measurementWindow.getAverageDailySteps();
    }
}
