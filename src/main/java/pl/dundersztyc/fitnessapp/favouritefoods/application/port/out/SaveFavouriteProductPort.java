package pl.dundersztyc.fitnessapp.favouritefoods.application.port.out;

import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;

public interface SaveFavouriteProductPort {
    boolean save(FavouriteProduct product);
}
