package pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in;

import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

public interface CalculateBfiUseCase {
    double calculateBfi(Gender gender, long neckCirc, long waistCirc, long hipCirc, Height height);
}
