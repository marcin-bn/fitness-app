package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

class OpenFoodFactsGetProductsByNameHandlerTest extends AbstractIntegrationTest {

    @Autowired
    private OpenFoodFactsGetProductsByNameHandler handler;

    @Test
    void shouldGetProductsByName() {
        var products = handler.getProductsByName("pizza", 3);

        assertThat(products).isNotNull();
        assertThat(products).hasSize(3);
    }

    @Test
    void shouldSetProductFieldsToNullWhenDataIsMissing() {
        // For some products, data is missing from the external API.
        var product = handler.getProductsByName("Italian tomatoes", 1).get(0);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
        // vitamin data + minerals missing
        assertThat(product.getVitaminD()).isNull();
        assertThat(product.getVitaminE()).isNull();
        assertThat(product.getPotassium()).isNull();
        assertThat(product.getMagnesium()).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"nonExistingProductName", "abcdef", "abc def"})
    void shouldReturnEmptyListWhenProductsWithGivenNameDoNotExist(String nonExistingProductName) {
        var products = handler.getProductsByName(nonExistingProductName, 1);
        assertThat(products).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenProductNameIsEmptyOrNull() {
        assertThat(handler.getProductsByName("", 1)).isEmpty();
        assertThat(handler.getProductsByName(null, 1)).isEmpty();
    }

}