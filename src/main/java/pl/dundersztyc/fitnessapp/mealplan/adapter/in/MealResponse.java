package pl.dundersztyc.fitnessapp.mealplan.adapter.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;

import java.time.LocalDate;

public record MealResponse(
        @NonNull Long id,
        @NonNull Long userId,
        @NonNull LocalDate day,
        @NonNull String productId,
        @NonNull Double weightInGrams) {

    public static MealResponse of(Meal meal) {
        return new MealResponse(
                meal.getId().value(),
                meal.getUserId().value(),
                meal.getDay(),
                meal.getProductId().value(),
                meal.getWeight().getGrams()
        );
    }
}
