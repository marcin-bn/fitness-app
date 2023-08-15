package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.food.application.NoSuchElementFoundException;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductByBarcodeUseCase;
import pl.dundersztyc.fitnessapp.food.domain.Product;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Primary
public class OpenFoodFactsGetProductByBarcodeService implements GetProductByBarcodeUseCase {

    private final OpenFoodFactsGetProductByIdHandler getProductByIdHandler;

    @Override
    public Product getProductByBarcode(String barcode) {
        String id = barcode; // In this api id is a barcode
        Optional<Product> product = getProductByIdHandler.getProductById(id);

        if (product.isEmpty()) {
            throw new NoSuchElementFoundException("cannot find product with given barcode");
        }
        return product.get();
    }
}
