package pl.dundersztyc.fitnessapp.food.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class Product {

    private final String id;
    private final String name;

    private final Double kcal;
    private final Nutrient fat;
    private final Nutrient proteins;
    private final Nutrient carbohydrates;
    private final Nutrient sugars;
    private final Nutrient salt;
    private final Nutrient vitaminA;
    private final Nutrient vitaminC;
    private final Nutrient vitaminD;
    private final Nutrient vitaminE;
    private final Nutrient vitaminK;
    private final Nutrient sodium;
    private final Nutrient calcium;
    private final Nutrient potassium;
    private final Nutrient magnesium;
    private final Nutrient iron;


}
