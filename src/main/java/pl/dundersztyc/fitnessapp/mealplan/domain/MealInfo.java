package pl.dundersztyc.fitnessapp.mealplan.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.domain.ProductNutritionFacts;


@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MealInfo {

    @NonNull
    private final Meal.MealId mealId;

    @NonNull
    private final String productName;

    @NonNull
    private final ProductNutritionFacts nutritionFacts;

    public static MealInfo valueOf(Meal.MealId mealId, Product product, Weight weight) {
        var weightMultiplier = calculateWeightMultiplier(product, weight);
        return new MealInfo(
                mealId,
                product.getName(),
                product.getNutritionFacts().multiply(weightMultiplier)
        );
    }

    private static double calculateWeightMultiplier(@NonNull Product product, @NonNull Weight weight) {
        return weight.getGrams() / product.getProductWeight().getGrams();
    }


}
