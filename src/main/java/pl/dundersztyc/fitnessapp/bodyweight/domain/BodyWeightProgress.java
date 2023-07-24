package pl.dundersztyc.fitnessapp.bodyweight.domain;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class BodyWeightProgress {

    private final static int SCALE = 2;
    private final static RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    @NonNull
    BigDecimal weightLoss;

    @NonNull
    BigDecimal weeklyWeightLoss;

    public BodyWeightProgress(@NonNull BigDecimal weightLoss, long weeks) {
        if (weeks < 0) {
            throw new IllegalArgumentException("weeks cannot be less than 0");
        }
        this.weightLoss = weightLoss;
        this.weeklyWeightLoss = (weeks == 0) ?
                BigDecimal.valueOf(0, SCALE) :
                weightLoss.divide(BigDecimal.valueOf(weeks), SCALE, ROUNDING_MODE);
    }


}
