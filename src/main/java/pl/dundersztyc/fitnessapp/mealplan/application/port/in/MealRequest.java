package pl.dundersztyc.fitnessapp.mealplan.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record MealRequest(@NotNull Long userId,
                          @NotNull String productId,
                          @NotNull LocalDate day,
                          @NotNull @Positive Double weightInGrams) {

    public MealRequest(
            Long userId,
            String productId,
            LocalDate day,
            Double weightInGrams) {
        this.userId = userId;
        this.productId = productId;
        this.day = day;
        this.weightInGrams = weightInGrams;
        validate(this);
    }

}

