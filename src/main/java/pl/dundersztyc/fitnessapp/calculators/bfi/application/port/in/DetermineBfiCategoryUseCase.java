package pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in;

import pl.dundersztyc.fitnessapp.calculators.bfi.domain.BfiCategory;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

public interface DetermineBfiCategoryUseCase {
    BfiCategory determineBfiCategory(double bfi, Gender gender);
}
