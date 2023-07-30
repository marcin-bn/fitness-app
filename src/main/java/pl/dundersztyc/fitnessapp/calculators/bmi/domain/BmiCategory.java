package pl.dundersztyc.fitnessapp.calculators.bmi.domain;

public enum BmiCategory {

    UNDER_WEIGHT     ("UNDER_WEIGHT", 0.0, 18.5),
    NORMAL_WEIGHT   ("NORMAL_WEIGHT", 18.5, 25.0),
    OVER_WEIGHT     ("OVER_WEIGHT", 25.0, 30.0),
    OBESE           ("OBESE", 30.0, 99.9);

    private final String name;
    private final double lowerLimit;
    private final double upperLimit;

    BmiCategory(String name, double lowerLimit, double upperLimit) {
        this.name = name;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public static BmiCategory determineCategory(double bmi) {
        for (var category : BmiCategory.values()) {
            if (category.lowerLimit <= bmi && bmi < category.upperLimit) {
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
