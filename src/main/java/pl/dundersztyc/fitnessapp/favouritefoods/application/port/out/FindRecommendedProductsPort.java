package pl.dundersztyc.fitnessapp.favouritefoods.application.port.out;

import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;

public interface FindRecommendedProductsPort {
    List<Product.ProductId> findRecommendedProductIds(User.UserId userId);
}
