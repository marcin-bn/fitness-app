package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.model.ProductData;

import java.util.function.Function;

@Component("openfoodfacts")
class ProductMapper {

    public Product mapToProduct(String productId, ProductData productData) {
        final var nutrients = productData.getNutrients();

        Function<String, Double> getNutrientQuantity = (nutrCode) -> {
            var nutrient = nutrients.get(nutrCode);
            Number quantity = (Number) nutrient;
            return (nutrient == null) ? null : quantity.doubleValue();
        };

        Double kcal = getNutrientQuantity.apply("energy-kcal_100g");
        Double fat = getNutrientQuantity.apply("fat_100g");
        Double proteins = getNutrientQuantity.apply("proteins_100g");
        Double carbohydrates = getNutrientQuantity.apply("carbohydrates_100g");
        Double sugars = getNutrientQuantity.apply("sugars_100g");
        Double salt = getNutrientQuantity.apply("salt_100g");
        Double vitaminA = getNutrientQuantity.apply("vitamin-a_100g");
        Double vitaminC = getNutrientQuantity.apply("vitamin-c_100g");
        Double vitaminD = getNutrientQuantity.apply("vitamin-d_100g");
        Double vitaminE = getNutrientQuantity.apply("vitamin-e_100g");
        Double vitaminK = getNutrientQuantity.apply("vitamin-k_100g");
        Double sodium = getNutrientQuantity.apply("sodium_100g");
        Double calcium = getNutrientQuantity.apply("calcium_100g");
        Double potassium = getNutrientQuantity.apply("potassium_100g");
        Double magnesium = getNutrientQuantity.apply("magnesium_100g");
        Double iron = getNutrientQuantity.apply("iron_100g");

        return Product.builder()
                .id(new Product.ProductId(productId))
                .name(productData.getProductName())
                .kcal(kcal)
                .fat(getNutrientFromGrams(fat))
                .proteins(getNutrientFromGrams(proteins))
                .carbohydrates(getNutrientFromGrams(carbohydrates))
                .sugars(getNutrientFromGrams(sugars))
                .salt(getNutrientFromGrams(salt))
                .vitaminA(getNutrientFromGrams(vitaminA))
                .vitaminC(getNutrientFromGrams(vitaminC))
                .vitaminD(getNutrientFromGrams(vitaminD))
                .vitaminE(getNutrientFromGrams(vitaminE))
                .vitaminK(getNutrientFromGrams(vitaminK))
                .sodium(getNutrientFromGrams(sodium))
                .calcium(getNutrientFromGrams(calcium))
                .potassium(getNutrientFromGrams(potassium))
                .magnesium(getNutrientFromGrams(magnesium))
                .iron(getNutrientFromGrams(iron))
                .build();
    }

    private Nutrient getNutrientFromGrams(Double quantity) {
        return getNutrient(quantity, Nutrient::fromGrams);
    }

    private Nutrient getNutrient(Double quantity, Function<Double, Nutrient> initializer) {
        return quantity == null ? null : initializer.apply(quantity);
    }

}
