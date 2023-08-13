package pl.dundersztyc.fitnessapp.food.domain;

import jakarta.validation.constraints.PositiveOrZero;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public class Nutrient {

    @PositiveOrZero
    private final double milligrams;

    private Nutrient(double milligrams) {
        this.milligrams = milligrams;
        validate(this);
    }

    public static Nutrient fromGrams(double grams) {
        return new Nutrient(grams * 1000);
    }

    public static Nutrient fromMilligrams(double milligrams) {
        return new Nutrient( milligrams);
    }

    public static Nutrient fromMicrograms(double micrograms) {
        return new Nutrient(micrograms / 1000);
    }

    public double getGrams() {
        return milligrams / 1000;
    }

    public double getMilligrams() {
        return milligrams;
    }

    public double getMicrograms() {
        return milligrams * 1000;
    }

}
