package pl.dundersztyc.fitnessapp.food.application.port.in;

import pl.dundersztyc.fitnessapp.food.domain.Product;

import java.util.List;

public interface GetProductsByNameUseCase {
    List<Product> getProductsByName(String name);
}
