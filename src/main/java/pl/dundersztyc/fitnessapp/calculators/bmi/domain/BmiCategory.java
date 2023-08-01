package pl.dundersztyc.fitnessapp.calculators.bmi.domain;

import pl.dundersztyc.fitnessapp.calculators.common.CategoryRange;

public enum BmiCategory {

    UNDER_WEIGHT("UNDER_WEIGHT", new CategoryRange(0.0, 18.5)),
    NORMAL_WEIGHT("NORMAL_WEIGHT", new CategoryRange(18.5, 25.0)),
    OVER_WEIGHT("OVER_WEIGHT", new CategoryRange(25.0, 30.0)),
    OBESE("OBESE", new CategoryRange(30.0, 99.9));

    private final String name;
    private final CategoryRange categoryRange;

    BmiCategory(String name, CategoryRange categoryRange) {
        this.name = name;
        this.categoryRange = categoryRange;
    }

    public static BmiCategory determineCategory(double bmi) {
        for (var category : BmiCategory.values()) {
            if (category.categoryRange.isInRange(bmi)) {
                return category;
            }
        }
        throw new IllegalArgumentException("BMI must be in range [0.0; 99.9)");
    }

    @Override
    public String toString() {
        return name;
    }
}
