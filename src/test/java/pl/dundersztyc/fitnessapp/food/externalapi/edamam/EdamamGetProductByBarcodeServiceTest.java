package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.food.application.NoSuchElementFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EdamamGetProductByBarcodeServiceTest extends AbstractIntegrationTest {

    @Autowired
    private EdamamGetProductByBarcodeService getProductByBarcodeService;

    @Test
    void shouldGetProductByBarcode() {
        var product= getProductByBarcodeService.getProductByBarcode("0049000051995");

        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
        assertThat(product.getId()).isNotBlank();
    }

    @Test
    void shouldThrowWhenBarcodeIsInvalid() {
        NoSuchElementFoundException exception = assertThrows(NoSuchElementFoundException.class,
               () -> {getProductByBarcodeService.getProductByBarcode("invalid barcode");}
        );
        assertThat(exception.getMessage()).isEqualTo("cannot find product with given barcode");
    }

}