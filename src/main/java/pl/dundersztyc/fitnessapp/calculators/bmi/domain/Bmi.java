package pl.dundersztyc.fitnessapp.calculators.bmi.domain;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public class Bmi {

    @Positive private final double weightInKg;
    @Positive private final double heightInM;

    public Bmi(double weightInKg,
               double heightInM
    ) {
        this.weightInKg = weightInKg;
        this.heightInM = heightInM;
        validate(this);
    }

    public double getValue() {
        return BigDecimal.valueOf(
                weightInKg / (heightInM * heightInM)
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
