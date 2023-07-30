package pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in;

public interface CalculateBmiUseCase {
    double calculateBmi(double weightInKg, double heightInM);
}
