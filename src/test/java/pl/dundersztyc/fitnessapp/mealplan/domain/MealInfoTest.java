package pl.dundersztyc.fitnessapp.mealplan.domain;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.dundersztyc.fitnessapp.common.ProductNutritionFactsData.defaultProductNutritionFacts;
import static pl.dundersztyc.fitnessapp.common.ProductTestData.defaultProduct;

class MealInfoTest {

    @Test
    void shouldConvertProductToMealInfo() {
        var weight = Weight.fromGrams(300);
        var product = defaultProduct()
                .nutritionFacts(defaultProductNutritionFacts().kcal(500.0).build())
                .build();

        var mealInfo = MealInfo.valueOf(new Meal.MealId(1L), product, weight);

        assertThat(mealInfo.getNutritionFacts().getKcal()).isEqualTo(1500.0);
    }

}