package pl.dundersztyc.fitnessapp.calculators.bmi.application;

import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in.CalculateBmiUseCase;
import pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in.DetermineBmiCategoryUseCase;
import pl.dundersztyc.fitnessapp.calculators.bmi.domain.Bmi;
import pl.dundersztyc.fitnessapp.calculators.bmi.domain.BmiCategory;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

@Service
class BmiCalculatorService implements CalculateBmiUseCase, DetermineBmiCategoryUseCase {

    @Override
    public double calculateBmi(Weight weight, Height height) {
        return new Bmi(weight, height).getValue();
    }

    @Override
    public BmiCategory determineBmiCategory(double bmi) {
        return BmiCategory.determineCategory(bmi);
    }
}
