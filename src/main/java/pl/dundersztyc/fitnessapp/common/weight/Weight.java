package pl.dundersztyc.fitnessapp.common.weight;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public class Weight {

    @Positive private final double weightInKg;

    private Weight(double weightInKg) {
        this.weightInKg = weightInKg;
        validate(this);
    }

    public static Weight fromKg(double weightInKg) {
        return new Weight(weightInKg);
    }

    public static Weight fromLbs(double weightInLbs) {
        return new Weight(weightInLbs * 0.4535);
    }

    public double getKg() {
        return BigDecimal.valueOf(
                weightInKg
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double getLbs() {
        return BigDecimal.valueOf(
                weightInKg / 0.4535
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


}
