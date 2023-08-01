package pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in;

import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

public interface CalculateBmiUseCase {
    double calculateBmi(Weight weight, Height height);
}
