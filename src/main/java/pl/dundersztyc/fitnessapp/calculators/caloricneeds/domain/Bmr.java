package pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

public class Bmr {

    public static long calculateBmr(long age, @NonNull Weight weight, @NonNull Height height, @NonNull Gender gender) {
        if (age <= 0) {
            throw new IllegalArgumentException("age cannot be less than or equal to 0");
        }

        if (gender == Gender.MAN) {
            return (long) (10.0 * weight.getKg() + 6.25 * height.getCm() - 5 * age + 5);
        }
        return (long) (10.0 * weight.getKg() + 6.25 * height.getCm() - 5 * age - 161);
    }
}
