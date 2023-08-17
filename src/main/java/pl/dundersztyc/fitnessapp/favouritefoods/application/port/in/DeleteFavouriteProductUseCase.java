package pl.dundersztyc.fitnessapp.favouritefoods.application.port.in;

import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

public interface DeleteFavouriteProductUseCase {
    void deleteFavouriteProduct(User.UserId userId, Product.ProductId productId);
}
