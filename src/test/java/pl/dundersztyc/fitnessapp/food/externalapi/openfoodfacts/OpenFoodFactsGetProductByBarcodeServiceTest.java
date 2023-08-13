package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OpenFoodFactsGetProductByBarcodeServiceTest extends AbstractIntegrationTest {

    @Autowired
    private OpenFoodFactsGetProductByBarcodeService getProductByBarcodeService;


    @Test
    void shouldGetProductByBarcode() {
        var product= getProductByBarcodeService.getProductByBarcode("3017620422003");

        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
        assertThat(product.getId()).isNotBlank();
    }

    @Test
    void shouldThrowWhenBarcodeIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {getProductByBarcodeService.getProductByBarcode("invalid barcode");}
        );
        assertThat(exception.getMessage()).isEqualTo("cannot find product with given barcode");
    }

}