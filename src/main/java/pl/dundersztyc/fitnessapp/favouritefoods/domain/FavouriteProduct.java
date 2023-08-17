package pl.dundersztyc.fitnessapp.favouritefoods.domain;

import lombok.Builder;
import lombok.NonNull;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;

@Builder
public record FavouriteProduct(
        Long id,
        @NonNull User.UserId userId,
        @NonNull Product.ProductId productId,
        @NonNull LocalDateTime timestamp) {
}
