package pl.dundersztyc.fitnessapp.favouritefoods.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

@Component
class FavouriteProductMapper {

    public FavouriteProduct mapToFavouriteProduct(FavouriteProductJpaEntity product) {
        return FavouriteProduct.builder()
                .id(product.getId())
                .userId(new User.UserId(product.getUserId()))
                .productId(new Product.ProductId(product.getProductId()))
                .timestamp(product.getTimestamp())
                .build();
    }

    public FavouriteProductJpaEntity mapToJpaEntity(FavouriteProduct product) {
        return FavouriteProductJpaEntity.builder()
                .id(product.id())
                .userId(product.userId().value())
                .productId(product.productId().value())
                .timestamp(product.timestamp())
                .build();
    }

}
