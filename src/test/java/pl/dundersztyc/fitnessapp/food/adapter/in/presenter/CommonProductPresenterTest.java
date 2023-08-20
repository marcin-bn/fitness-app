package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.ProductTestData.defaultProduct;

class CommonProductPresenterTest {

    @Test
    void shouldPrepareView() {
        var productResponse = ProductResponse.of(defaultProduct().build());

        var view = new CommonProductPresenter().prepareView(productResponse);

        var nutritionFacts = view.getNutritionFacts();
        assertThat(nutritionFacts.getVitaminA()).isEqualTo(null);
        assertThat(nutritionFacts.getVitaminC()).isEqualTo(null);
        assertThat(nutritionFacts.getVitaminD()).isEqualTo(null);
        assertThat(nutritionFacts.getVitaminE()).isEqualTo(null);
        assertThat(nutritionFacts.getVitaminK()).isEqualTo(null);
        assertThat(nutritionFacts.getMagnesium()).isEqualTo(null);
    }

}