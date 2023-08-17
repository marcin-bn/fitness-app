package pl.dundersztyc.fitnessapp.favouritefoods.application;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.SaveFavouriteProductPort;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class FavouriteFoodsServiceTest {

    private final SaveFavouriteProductPort saveFavouriteProductPort = mock(SaveFavouriteProductPort.class);
    private final FavouriteFoodsService favouriteFoodsService =
            new FavouriteFoodsService(saveFavouriteProductPort, null, null, null);

    @Test
    void shouldAddFavouriteProduct() {
        var userId = new User.UserId(1L);
        var productId = new Product.ProductId("abc");

        var favouriteProduct = favouriteFoodsService.addFavouriteProduct(userId, productId);

        then(saveFavouriteProductPort).should(times(1))
                .save(favouriteProduct);
        assertThat(favouriteProduct).isNotNull();
        assertThat(favouriteProduct.userId()).isEqualTo(userId);
        assertThat(favouriteProduct.productId()).isEqualTo(productId);
    }

}