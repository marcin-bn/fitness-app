package pl.dundersztyc.fitnessapp.favouritefoods.adapter.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;

public record FavouriteProductResponse(
        @NonNull Long userId,
        @NonNull String productId
) {
    public static FavouriteProductResponse of(FavouriteProduct product) {
        return new FavouriteProductResponse(product.userId().value(), product.productId().value());
    }
}
