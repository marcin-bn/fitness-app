package pl.dundersztyc.fitnessapp.calculators.bfi.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public class Bfi {

    @NotNull private final Gender gender;
    @Positive private final long neckCirc;
    @Positive private final long waistCirc;
    @Positive private final long hipCirc;
    @NotNull private final Height height;

    public Bfi(Gender gender, long neckCirc, long waistCirc, long hipCirc, Height height) {
        this.gender = gender;
        this.neckCirc = neckCirc;
        this.waistCirc = waistCirc;
        this.hipCirc = hipCirc;
        this.height = height;
        validate(this);
    }

    public double getValue() {
        if (gender == Gender.MAN) {
            return BigDecimal.valueOf(
                    495 /
                    (1.0324 - 0.19077 * Math.log10(waistCirc - neckCirc) + 0.15456 * Math.log10(height.getCm()))
                    - 450
            ).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        return BigDecimal.valueOf(
                495 /
                (1.29579 - 0.35004 * Math.log10(waistCirc + hipCirc - neckCirc) + 0.22100 * Math.log10(height.getCm()))
                - 450
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
