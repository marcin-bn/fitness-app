package pl.dundersztyc.fitnessapp.calculators.bfi.application;

import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in.CalculateBfiUseCase;
import pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in.DetermineBfiCategoryUseCase;
import pl.dundersztyc.fitnessapp.calculators.bfi.domain.Bfi;
import pl.dundersztyc.fitnessapp.calculators.bfi.domain.BfiCategory;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

@Service
class BfiCalculatorService implements CalculateBfiUseCase, DetermineBfiCategoryUseCase {

    @Override
    public double calculateBfi(Gender gender, long neckCirc, long waistCirc, long hipCirc, Height height) {
        return new Bfi(gender, neckCirc, waistCirc, hipCirc, height).getValue();
    }

    @Override
    public BfiCategory determineBfiCategory(double bfi, Gender gender) {
        return BfiCategory.determineCategory(bfi, gender);
    }
}
