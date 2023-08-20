package pl.dundersztyc.fitnessapp.food.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductNutritionFacts {

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

    public ProductNutritionFacts multiply(double weightMultiplier) {
        return ProductNutritionFacts.builder()
                .kcal(kcal == null ? null : kcal * weightMultiplier)
                .fat(multiplyNutrient(fat, weightMultiplier))
                .proteins(multiplyNutrient(proteins, weightMultiplier))
                .carbohydrates(multiplyNutrient(carbohydrates, weightMultiplier))
                .sugars(multiplyNutrient(sugars, weightMultiplier))
                .salt(multiplyNutrient(salt, weightMultiplier))
                .vitaminA(multiplyNutrient(vitaminA, weightMultiplier))
                .vitaminC(multiplyNutrient(vitaminC, weightMultiplier))
                .vitaminD(multiplyNutrient(vitaminD, weightMultiplier))
                .vitaminE(multiplyNutrient(vitaminE, weightMultiplier))
                .vitaminK(multiplyNutrient(vitaminK, weightMultiplier))
                .sodium(multiplyNutrient(sodium, weightMultiplier))
                .calcium(multiplyNutrient(calcium, weightMultiplier))
                .potassium(multiplyNutrient(potassium, weightMultiplier))
                .magnesium(multiplyNutrient(magnesium, weightMultiplier))
                .iron(multiplyNutrient(iron, weightMultiplier))
                .build();
    }

    public ProductNutritionFacts add(ProductNutritionFacts nutrition) {
        if (nutrition == null) return this;
        return ProductNutritionFacts.builder()
                .kcal(kcal == null ? null : kcal + nutrition.kcal)
                .fat(addNutrient(fat, nutrition.fat))
                .proteins(addNutrient(proteins, nutrition.proteins))
                .carbohydrates(addNutrient(carbohydrates, nutrition.carbohydrates))
                .sugars(addNutrient(sugars, nutrition.sugars))
                .salt(addNutrient(salt, nutrition.salt))
                .vitaminA(addNutrient(vitaminA, nutrition.vitaminA))
                .vitaminC(addNutrient(vitaminC, nutrition.vitaminC))
                .vitaminD(addNutrient(vitaminD, nutrition.vitaminD))
                .vitaminE(addNutrient(vitaminE, nutrition.vitaminE))
                .vitaminK(addNutrient(vitaminK, nutrition.vitaminK))
                .sodium(addNutrient(sodium, nutrition.sodium))
                .calcium(addNutrient(calcium, nutrition.calcium))
                .potassium(addNutrient(potassium, nutrition.potassium))
                .magnesium(addNutrient(magnesium, nutrition.magnesium))
                .iron(addNutrient(iron, nutrition.iron))
                .build();
    }

    private Nutrient multiplyNutrient(Nutrient nutrient, double weightMultiplier) {
        return nutrient == null ? null : nutrient.multiply(weightMultiplier);
    }

    private Nutrient addNutrient(Nutrient lhs, Nutrient rhs) {
        if (lhs == null && rhs == null) return null;
        else if (lhs == null) return rhs;
        else if (rhs == null) return lhs;
        return Nutrient.fromGrams(lhs.getGrams() + rhs.getGrams());
    }

}
