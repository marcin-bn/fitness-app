package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.edamam.model.ProductData;

import java.util.function.Function;

@Component("edamam")
class ProductMapper {

    public Product mapToProduct(String productId, ProductData productData) {
        final var nutrients = productData.getTotalNutrients();

        Function<String, Double> getNutrientQuantity = (nutrCode) -> {
            var nutrient = nutrients.get(nutrCode);
            return (nutrient == null) ? null : nutrient.getQuantity();
        };

        Double kcal = getNutrientQuantity.apply("ENERC_KCAL");
        Double fat = getNutrientQuantity.apply("FAT");
        Double proteins = getNutrientQuantity.apply("PROCNT");
        Double carbohydrates = getNutrientQuantity.apply("CHOCDF");
        Double sugars = getNutrientQuantity.apply("SUGAR");
        Double vitaminA = getNutrientQuantity.apply("VITA_RAE");
        Double vitaminC = getNutrientQuantity.apply("VITC");
        Double vitaminD = getNutrientQuantity.apply("VITD");
        Double vitaminE = getNutrientQuantity.apply("TOCPHA");
        Double vitaminK = getNutrientQuantity.apply("VITK1");
        Double sodium = getNutrientQuantity.apply("NA");
        Double calcium = getNutrientQuantity.apply("CA");
        Double potassium = getNutrientQuantity.apply("K");
        Double magnesium = getNutrientQuantity.apply("MG");
        Double iron = getNutrientQuantity.apply("FE");
        Double salt = convertSodiumToSalt(sodium);


        return Product.builder()
                .id(new Product.ProductId(productId))
                .name(productData.getProductName())
                .kcal(kcal)
                .fat(getNutrient(fat, Nutrient::fromGrams))
                .proteins(getNutrient(proteins, Nutrient::fromGrams))
                .carbohydrates(getNutrient(carbohydrates, Nutrient::fromGrams))
                .sugars(getNutrient(sugars, Nutrient::fromGrams))
                .salt(getNutrient(salt, Nutrient::fromMilligrams))
                .vitaminA(getNutrient(vitaminA, Nutrient::fromMicrograms))
                .vitaminC(getNutrient(vitaminC, Nutrient::fromMilligrams))
                .vitaminD(getNutrient(vitaminD, Nutrient::fromMicrograms))
                .vitaminE(getNutrient(vitaminE, Nutrient::fromMilligrams))
                .vitaminK(getNutrient(vitaminK, Nutrient::fromMicrograms))
                .sodium(getNutrient(sodium, Nutrient::fromMilligrams))
                .calcium(getNutrient(calcium, Nutrient::fromMilligrams))
                .potassium(getNutrient(potassium, Nutrient::fromMilligrams))
                .magnesium(getNutrient(magnesium, Nutrient::fromMilligrams))
                .iron(getNutrient(iron, Nutrient::fromMilligrams))
                .build();
    }

    private Nutrient getNutrient(Double quantity, Function<Double, Nutrient> initializer) {
        return quantity == null ? null : initializer.apply(quantity);
    }

    private Double convertSodiumToSalt(Double sodium) {
        return sodium == null ? null : sodium * 2.5 / 1000;
    }
}
