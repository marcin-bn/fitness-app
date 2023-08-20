package pl.dundersztyc.fitnessapp.mealplan.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;

@Value
@RequiredArgsConstructor
@Builder
public class Meal {

    private MealId id;

    @NonNull
    private  final User.UserId userId;

    @NonNull
    private final LocalDate day;

    @NonNull
    private final Product.ProductId productId;

    @NonNull
    private final Weight weight;


    public record MealId(@NonNull Long value) {
    }

}
