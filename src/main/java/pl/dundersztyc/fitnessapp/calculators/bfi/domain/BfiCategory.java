package pl.dundersztyc.fitnessapp.calculators.bfi.domain;


import pl.dundersztyc.fitnessapp.calculators.common.CategoryRange;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

public enum BfiCategory {

    BELOW_STANDARD("BELOW_STANDARD", new CategoryRange(0.0, 10.0), new CategoryRange(0.0, 2.0)),
    ESSENTIAL_FAT("ESSENTIAL_FAT", new CategoryRange(10.0, 14.0), new CategoryRange(2.0, 6.0)),
    ATHLETES("ATHLETES", new CategoryRange(14.0, 21.0), new CategoryRange(6.0, 14.0)),
    FITNESS("FITNESS", new CategoryRange(21.0, 25.0), new CategoryRange(14.0, 18.0)),
    AVERAGE("AVERAGE", new CategoryRange(25.0, 32.0), new CategoryRange(18.0, 25.0)),
    OBESE("OBESE", new CategoryRange(32.0, 99.9), new CategoryRange(25.0, 99.9));

    private final String name;
    private final CategoryRange womanCategoryRange;
    private final CategoryRange manCategoryRange;

    BfiCategory(String name, CategoryRange womanCategoryRange, CategoryRange manCategoryRange) {
        this.name = name;
        this.womanCategoryRange = womanCategoryRange;
        this.manCategoryRange = manCategoryRange;
    }

    public static BfiCategory determineCategory(double bfi, Gender gender) {
        for (var category : BfiCategory.values()) {
            if ((gender == Gender.MAN && category.manCategoryRange.isInRange(bfi)) ||
                (gender == Gender.WOMAN && category.womanCategoryRange.isInRange(bfi))
            ) {
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

