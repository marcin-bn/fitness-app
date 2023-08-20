package pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.adapter.in.NutritionFactsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.ProductNutritionFactsData.defaultProductNutritionFacts;

class CommonNutrientPresenterTest {

    @Test
    void shouldPrepareView() {
        var nutritionResponse = NutritionFactsResponse.of(defaultProductNutritionFacts().build());

        var view = new CommonNutrientPresenter().prepareView(nutritionResponse);

        assertThat(view.getVitaminA()).isEqualTo(null);
        assertThat(view.getVitaminC()).isEqualTo(null);
        assertThat(view.getVitaminD()).isEqualTo(null);
        assertThat(view.getVitaminE()).isEqualTo(null);
        assertThat(view.getVitaminK()).isEqualTo(null);
        assertThat(view.getMagnesium()).isEqualTo(null);
    }
}