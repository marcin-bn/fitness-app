package pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.adapter.in.NutritionFactsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.ProductNutritionFactsData.defaultProductNutritionFacts;

class PremiumNutrientPresenterTest {

    @Test
    void shouldPrepareView() {
        var nutritionResponse = NutritionFactsResponse.of(defaultProductNutritionFacts().kcal(123.0).build());

        var view = new PremiumNutrientPresenter().prepareView(nutritionResponse);

        assertThat(nutritionResponse).isEqualTo(view);
        assertThat(view.getKcal()).isEqualTo(123.0);
    }

}