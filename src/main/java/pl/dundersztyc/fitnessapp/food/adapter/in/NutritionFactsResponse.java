package pl.dundersztyc.fitnessapp.food.adapter.in;

import lombok.*;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;
import pl.dundersztyc.fitnessapp.food.domain.ProductNutritionFacts;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NutritionFactsResponse {

    private Double kcal;
    private OptWrapper<NutrientResponse> fat;
    private OptWrapper<NutrientResponse> proteins;
    private OptWrapper<NutrientResponse> carbohydrates;
    private OptWrapper<NutrientResponse> sugars;
    private OptWrapper<NutrientResponse> salt;
    private OptWrapper<NutrientResponse> vitaminA;
    private OptWrapper<NutrientResponse> vitaminC;
    private OptWrapper<NutrientResponse> vitaminD;
    private OptWrapper<NutrientResponse> vitaminE;
    private OptWrapper<NutrientResponse> vitaminK;
    private OptWrapper<NutrientResponse> sodium;
    private OptWrapper<NutrientResponse> calcium;
    private OptWrapper<NutrientResponse> potassium;
    private OptWrapper<NutrientResponse> magnesium;
    private OptWrapper<NutrientResponse> iron;


    public static NutritionFactsResponse of(ProductNutritionFacts nutritionFacts) {
        return NutritionFactsResponse.builder()
                .kcal(nutritionFacts.getKcal())
                .fat(getNutrient(nutritionFacts.getFat(), NutrientResponse.Unit.Gram))
                .proteins(getNutrient(nutritionFacts.getProteins(), NutrientResponse.Unit.Gram))
                .carbohydrates(getNutrient(nutritionFacts.getCarbohydrates(), NutrientResponse.Unit.Gram))
                .sugars(getNutrient(nutritionFacts.getSugars(), NutrientResponse.Unit.Gram))
                .salt(getNutrient(nutritionFacts.getSalt(), NutrientResponse.Unit.Gram))
                .vitaminA(getNutrient(nutritionFacts.getVitaminA(), NutrientResponse.Unit.Microgram))
                .vitaminC(getNutrient(nutritionFacts.getVitaminC(), NutrientResponse.Unit.Milligram))
                .vitaminD(getNutrient(nutritionFacts.getVitaminD(), NutrientResponse.Unit.Microgram))
                .vitaminE(getNutrient(nutritionFacts.getVitaminE(), NutrientResponse.Unit.Milligram))
                .vitaminK(getNutrient(nutritionFacts.getVitaminK(), NutrientResponse.Unit.Microgram))
                .sodium(getNutrient(nutritionFacts.getSodium(), NutrientResponse.Unit.Milligram))
                .sodium(getNutrient(nutritionFacts.getSodium(), NutrientResponse.Unit.Milligram))
                .calcium(getNutrient(nutritionFacts.getCalcium(), NutrientResponse.Unit.Milligram))
                .potassium(getNutrient(nutritionFacts.getPotassium(), NutrientResponse.Unit.Milligram))
                .magnesium(getNutrient(nutritionFacts.getMagnesium(), NutrientResponse.Unit.Milligram))
                .iron(getNutrient(nutritionFacts.getIron(), NutrientResponse.Unit.Milligram))
                .build();
    }

    private static OptWrapper<NutrientResponse> getNutrient(Nutrient nutrient, NutrientResponse.Unit unit) {
        return new OptWrapper<>(Optional.ofNullable(
                NutrientResponse.of(nutrient, unit))
        );
    }
}
