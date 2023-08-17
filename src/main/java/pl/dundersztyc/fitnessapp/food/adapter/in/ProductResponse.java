package pl.dundersztyc.fitnessapp.food.adapter.in;

import lombok.*;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;
import pl.dundersztyc.fitnessapp.food.domain.Product;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private String id;
    private String name;
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

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId().value())
                .name(product.getName())
                .kcal(product.getKcal())
                .fat(getNutrient(product.getFat(), NutrientResponse.Unit.Gram))
                .proteins(getNutrient(product.getProteins(), NutrientResponse.Unit.Gram))
                .carbohydrates(getNutrient(product.getCarbohydrates(), NutrientResponse.Unit.Gram))
                .sugars(getNutrient(product.getSugars(), NutrientResponse.Unit.Gram))
                .salt(getNutrient(product.getSalt(), NutrientResponse.Unit.Gram))
                .vitaminA(getNutrient(product.getVitaminA(), NutrientResponse.Unit.Microgram))
                .vitaminC(getNutrient(product.getVitaminC(), NutrientResponse.Unit.Milligram))
                .vitaminD(getNutrient(product.getVitaminD(), NutrientResponse.Unit.Microgram))
                .vitaminE(getNutrient(product.getVitaminE(), NutrientResponse.Unit.Milligram))
                .vitaminK(getNutrient(product.getVitaminK(), NutrientResponse.Unit.Microgram))
                .sodium(getNutrient(product.getSodium(), NutrientResponse.Unit.Milligram))
                .sodium(getNutrient(product.getSodium(), NutrientResponse.Unit.Milligram))
                .calcium(getNutrient(product.getCalcium(), NutrientResponse.Unit.Milligram))
                .potassium(getNutrient(product.getPotassium(), NutrientResponse.Unit.Milligram))
                .magnesium(getNutrient(product.getMagnesium(), NutrientResponse.Unit.Milligram))
                .iron(getNutrient(product.getIron(), NutrientResponse.Unit.Milligram))
                .build();
    }


    private static OptWrapper<NutrientResponse> getNutrient(Nutrient nutrient, NutrientResponse.Unit unit) {
        return new OptWrapper<>(Optional.ofNullable(
                NutrientResponse.of(nutrient, unit))
        );
    }

}

