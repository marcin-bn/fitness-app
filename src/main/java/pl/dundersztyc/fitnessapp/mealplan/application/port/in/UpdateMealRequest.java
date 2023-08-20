package pl.dundersztyc.fitnessapp.mealplan.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record UpdateMealRequest(@NotNull Long id,
                                @NotNull Long userId,
                                @NotNull String productId,
                                @NotNull LocalDate day,
                                @NotNull @Positive Double weightInGrams) {

    public UpdateMealRequest(
            Long id,
            Long userId,
            String productId,
            LocalDate day,
            Double weightInGrams) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.day = day;
        this.weightInGrams = weightInGrams;
        validate(this);
    }

}
