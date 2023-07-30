package pl.dundersztyc.fitnessapp.calculators.bmi.application;

import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in.CalculateBmiUseCase;
import pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in.DetermineBmiCategoryUseCase;
import pl.dundersztyc.fitnessapp.calculators.bmi.domain.Bmi;
import pl.dundersztyc.fitnessapp.calculators.bmi.domain.BmiCategory;

@Service
class BmiCalculatorService implements CalculateBmiUseCase, DetermineBmiCategoryUseCase {

    @Override
    public double calculateBmi(double weightInKg, double heightInM) {
        return new Bmi(weightInKg, heightInM).getValue();
    }

    @Override
    public BmiCategory determineBmiCategory(double bmi) {
        return BmiCategory.determineCategory(bmi);
    }
}
