package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.food.domain.Product;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OpenFoodFactsGetProductByIdHandlerTest extends AbstractIntegrationTest {

    @Autowired
    private OpenFoodFactsGetProductByIdHandler handler;

    @Test
    void shouldGetProductById() {
        Optional<Product> optProduct = handler.getProductById("3017620422003");

        assertThat(optProduct).isPresent();
        assertThat(optProduct.get()).isNotNull();
    }

    @Test
    void shouldSetFieldsToNullWhenDataIsMissing() {
        // For some products, data is missing from the external API.
        Optional<Product> optProduct = handler.getProductById("5901135000949");

        assertThat(optProduct).isPresent();
        assertThat(optProduct.get()).isNotNull();

        var product = optProduct.get();

        assertThat(product.getId()).isNotNull();
        // vitamin data missing
        var nutritionFacts = product.getNutritionFacts();
        assertThat(nutritionFacts.getVitaminA()).isNull();
        assertThat(nutritionFacts.getVitaminC()).isNull();
        assertThat(nutritionFacts.getVitaminD()).isNull();
        assertThat(nutritionFacts.getVitaminE()).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "thisIdDoesNotExist"})
    void shouldReturnEmptyOptionalWhenProductIdDoesNotExist(String nonExistingId) {
        Optional<Product> product = handler.getProductById(nonExistingId);

        assertThat(product).isEmpty();
    }


}