package pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain;

public enum BodyWeightGoal {
    MAINTAIN_WEIGHT(0),
    MILD_WEIGHT_LOSS(-250),
    WEIGHT_LOSS(-500),
    MILD_WEIGHT_GAIN(250),
    WEIGHT_GAIN(500);

    private final long caloricBonus;

    BodyWeightGoal(long caloricBonus) {
        this.caloricBonus = caloricBonus;
    }

    public long getCaloricNeeds(long basicCaloricNeeds) {
        if (basicCaloricNeeds <= 0) {
            throw new IllegalArgumentException("basicCaloricNeeds cannot be smaller than or equal to 0");
        }
        return basicCaloricNeeds + caloricBonus;
    }
}
