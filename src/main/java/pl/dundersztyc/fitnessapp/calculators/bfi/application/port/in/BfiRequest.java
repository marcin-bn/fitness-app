package pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record BfiRequest(
        @NotNull Gender gender,
        @Positive long neckCirc,
        @Positive long waistCirc,
        @Positive long hipCirc,
        @Positive long heightInCm
) {
    public BfiRequest(Gender gender, long neckCirc, long waistCirc, long hipCirc, long heightInCm) {
        this.gender = gender;
        this.neckCirc = neckCirc;
        this.waistCirc = waistCirc;
        this.hipCirc = hipCirc;
        this.heightInCm = heightInCm;
        validate(this);
    }
}
