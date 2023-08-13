package pl.dundersztyc.fitnessapp.food.application.port.in;

import pl.dundersztyc.fitnessapp.food.domain.Product;

public interface GetProductByIdUseCase {
    Product getProductById(String productId);
}
