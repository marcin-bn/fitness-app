package pl.dundersztyc.fitnessapp.common.height;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public class Height {

    @Positive private final long heightInCm;

    private Height(long heightInCm) {
        this.heightInCm = heightInCm;
        validate(this);
    }

    public static Height fromCm(long heightInCm) {
        return new Height(heightInCm);
    }

    public static Height fromM(double heightInM) {
        return new Height((long) (heightInM * 100));
    }

    public static Height fromInches(double heightInInches) {
        return new Height((long) (heightInInches * 2.54));
    }

    public long getCm() {
        return heightInCm;
    }

    public double getM() {
        return BigDecimal.valueOf(
                heightInCm / 100.0
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public long getInches() {
        return (long) (heightInCm / 2.54);
    }
}
