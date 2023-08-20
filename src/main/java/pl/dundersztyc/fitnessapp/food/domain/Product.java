package pl.dundersztyc.fitnessapp.food.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class Product {

    private final ProductId id;
    private final String name;

    private final ProductNutritionFacts nutritionFacts;

    private final Nutrient productWeight = Nutrient.fromGrams(100);

    public record ProductId(@NonNull String value) {
    }


}
