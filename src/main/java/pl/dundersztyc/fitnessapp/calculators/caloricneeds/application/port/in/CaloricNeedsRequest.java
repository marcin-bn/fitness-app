package pl.dundersztyc.fitnessapp.calculators.caloricneeds.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.ActivityFrequency;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.BodyWeightGoal;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record CaloricNeedsRequest(
        @Positive long age,
        @NotNull Gender gender,
        @Positive long heightInCm,
        @Positive double weightInKg,
        @NotNull ActivityFrequency activityFrequency,
        @NotNull BodyWeightGoal weightGoal
) {
    public CaloricNeedsRequest(long age, Gender gender, long heightInCm, double weightInKg, ActivityFrequency activityFrequency, BodyWeightGoal weightGoal) {
        this.age = age;
        this.gender = gender;
        this.heightInCm = heightInCm;
        this.weightInKg = weightInKg;
        this.activityFrequency = activityFrequency;
        this.weightGoal = weightGoal;
        validate(this);
    }
}
