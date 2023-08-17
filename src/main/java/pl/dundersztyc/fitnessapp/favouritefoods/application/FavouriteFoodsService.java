package pl.dundersztyc.fitnessapp.favouritefoods.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.AddFavouriteProductUseCase;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.DeleteFavouriteProductUseCase;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.GetFavouriteProductsUseCase;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.GetRecommendedProductsUseCase;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.DeleteFavouriteProductPort;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.FindRecommendedProductsPort;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.LoadFavouriteProductsPort;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.SaveFavouriteProductPort;
import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class FavouriteFoodsService implements
        AddFavouriteProductUseCase, DeleteFavouriteProductUseCase, GetFavouriteProductsUseCase, GetRecommendedProductsUseCase {

    private final SaveFavouriteProductPort saveFavouriteProductPort;
    private final DeleteFavouriteProductPort deleteFavouriteProductPort;
    private final LoadFavouriteProductsPort loadFavouriteProductsPort;
    private final FindRecommendedProductsPort findRecommendedProductsPort;

    @Override
    public FavouriteProduct addFavouriteProduct(User.UserId userId, Product.ProductId productId) {
        var favouriteProduct = FavouriteProduct.builder()
                .userId(userId)
                .productId(productId)
                .timestamp(LocalDateTime.now())
                .build();

        saveFavouriteProductPort.save(favouriteProduct);

        return favouriteProduct;
    }

    @Override
    @Transactional
    public void deleteFavouriteProduct(User.UserId userId, Product.ProductId productId) {
        deleteFavouriteProductPort.deleteFavouriteProduct(userId, productId);
    }

    @Override
    public List<FavouriteProduct> getFavouriteProductsByUserId(User.UserId userId) {
        return loadFavouriteProductsPort.findByUserId(userId);
    }

    @Override
    public List<Product.ProductId> getRecommendedProductIds(User.UserId userId) {
        return findRecommendedProductsPort.findRecommendedProductIds(userId);
    }
}
