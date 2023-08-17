package pl.dundersztyc.fitnessapp.favouritefoods.application.port.out;

import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

public interface DeleteFavouriteProductPort {
    void deleteFavouriteProduct(User.UserId userId, Product.ProductId productId);
}
