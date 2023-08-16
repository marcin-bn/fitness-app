package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.dundersztyc.fitnessapp.common.ProductTestData.defaultProduct;

class CommonProductPresenterTest {

    @Test
    void shouldPrepareView() {
        var productResponse = ProductResponse.of(defaultProduct().build());

        var view = new CommonProductPresenter().prepareView(productResponse);

        assertThat(view.getVitaminA()).isEqualTo(null);
        assertThat(view.getVitaminC()).isEqualTo(null);
        assertThat(view.getVitaminD()).isEqualTo(null);
        assertThat(view.getVitaminE()).isEqualTo(null);
        assertThat(view.getVitaminK()).isEqualTo(null);
        assertThat(view.getMagnesium()).isEqualTo(null);
    }

}