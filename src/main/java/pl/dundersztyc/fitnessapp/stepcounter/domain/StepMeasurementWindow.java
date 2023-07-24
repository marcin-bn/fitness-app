package pl.dundersztyc.fitnessapp.stepcounter.domain;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StepMeasurementWindow {

    private List<StepMeasurement> measurements;

    public StepMeasurementWindow(@NonNull List<StepMeasurement> measurements) {
        this.measurements = measurements;
    }

    public StepMeasurementWindow(@NonNull StepMeasurement... measurements) {
        this.measurements = new ArrayList<>(Arrays.asList(measurements));
    }

    public void addMeasurement(StepMeasurement measurement) {
        this.measurements.add(measurement);
    }

    public List<StepMeasurement> getMeasurements() {
        return Collections.unmodifiableList(measurements);
    }


}
