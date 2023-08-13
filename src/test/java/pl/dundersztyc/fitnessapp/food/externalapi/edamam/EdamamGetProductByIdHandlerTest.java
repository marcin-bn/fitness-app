package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.food.domain.Product;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EdamamGetProductByIdHandlerTest extends AbstractIntegrationTest {

    @Autowired
    private EdamamGetProductByIdHandler handler;

    @Test
    void shouldGetProductById() {
        Optional<Product> optProduct = handler.getProductById("food_at830s9amds32fb8w6ufmaopzk8n");

        assertThat(optProduct).isPresent();
        assertThat(optProduct.get()).isNotNull();
    }

    @Test
    void shouldSetFieldsToNullWhenDataIsMissing() {
        // For some products, data is missing from the external API.
        Optional<Product> optProduct = handler.getProductById("food_a8dytckbmjx99eaduccs6bowpa5m");

        assertThat(optProduct).isPresent();
        assertThat(optProduct.get()).isNotNull();

        var product = optProduct.get();

        assertThat(product.getId()).isNotNull();
        // vitamin data missing
        assertThat(product.getVitaminA()).isNull();
        assertThat(product.getVitaminC()).isNull();
        assertThat(product.getVitaminD()).isNull();
        assertThat(product.getVitaminE()).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"food_abc", "abc"})
    void shouldReturnEmptyOptionalWhenProductIdDoesNotExist(String nonExistingId) {
        Optional<Product> product = handler.getProductById(nonExistingId);

        assertThat(product).isEmpty();
    }


}