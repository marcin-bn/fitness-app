package pl.dundersztyc.fitnessapp.favouritefoods.application.port.out;

import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;

public interface LoadFavouriteProductsPort {
    List<FavouriteProduct> findByUserId(User.UserId userId);
}
