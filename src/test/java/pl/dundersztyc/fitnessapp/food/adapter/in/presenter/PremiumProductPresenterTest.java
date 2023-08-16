package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PremiumProductPresenterTest {

    @Test
    void shouldPrepareView() {
        var productResponse = ProductResponse.builder().id("ID_123").build();

        var view = new PremiumProductPresenter().prepareView(productResponse);

        assertThat(productResponse).isEqualTo(view);
    }
}