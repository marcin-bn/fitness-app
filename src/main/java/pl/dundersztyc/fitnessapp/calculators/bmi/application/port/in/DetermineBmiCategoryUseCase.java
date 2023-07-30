package pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in;

import pl.dundersztyc.fitnessapp.calculators.bmi.domain.BmiCategory;

public interface DetermineBmiCategoryUseCase {
    BmiCategory determineBmiCategory(double bmi);
}
