package pl.dundersztyc.fitnessapp.favouritefoods.application.port.in;

import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;

public interface GetFavouriteProductsUseCase {
    List<FavouriteProduct> getFavouriteProductsByUserId(User.UserId userId);
}
