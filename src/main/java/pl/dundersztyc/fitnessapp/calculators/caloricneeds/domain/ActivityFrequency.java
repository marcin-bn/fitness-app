package pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain;

public enum ActivityFrequency {
    LITTLE(1.2),
    LIGHT(1.375),
    MODERATE(1.465),
    ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    EXTRA_ACTIVE(1.9);

    private final double bmrMultiplier;

    ActivityFrequency(double bmrMultiplier) {
        this.bmrMultiplier = bmrMultiplier;
    }

    public long getBasicCaloricNeeds(long bmr) {
        if (bmr <= 0) {
            throw new IllegalArgumentException("bmr cannot be smaller than or equal to 0");
        }
        return (long) (bmr * bmrMultiplier);
    }
}
