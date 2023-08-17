package pl.dundersztyc.fitnessapp.favouritefoods.application.port.in;

import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;

public interface GetRecommendedProductsUseCase {
    List<Product.ProductId> getRecommendedProductIds(User.UserId userId);
}
