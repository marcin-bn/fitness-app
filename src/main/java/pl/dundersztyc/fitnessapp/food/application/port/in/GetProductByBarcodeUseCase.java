package pl.dundersztyc.fitnessapp.food.application.port.in;

import pl.dundersztyc.fitnessapp.food.domain.Product;

public interface GetProductByBarcodeUseCase {
    Product getProductByBarcode(String barcode);
}
