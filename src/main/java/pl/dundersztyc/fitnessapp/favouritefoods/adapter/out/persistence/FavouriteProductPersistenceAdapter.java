package pl.dundersztyc.fitnessapp.favouritefoods.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.DeleteFavouriteProductPort;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.FindRecommendedProductsPort;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.LoadFavouriteProductsPort;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.out.SaveFavouriteProductPort;
import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class FavouriteProductPersistenceAdapter implements
        LoadFavouriteProductsPort, SaveFavouriteProductPort, FindRecommendedProductsPort, DeleteFavouriteProductPort {

    private final FavouriteProductRepository favouriteProductRepository;
    private final FavouriteProductMapper favouriteProductMapper;

    @Override
    public List<FavouriteProduct> findByUserId(User.UserId userId) {
        var products = favouriteProductRepository.findByUserId(userId.value())
                .orElseThrow(EntityNotFoundException::new);

        return products.stream()
                .map(favouriteProductMapper::mapToFavouriteProduct)
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(FavouriteProduct product) {
        if (product == null) return false;
        var productEntity = favouriteProductMapper.mapToJpaEntity(product);
        favouriteProductRepository.save(productEntity);
        return true;
    }

    @Override
    public List<Product.ProductId> findRecommendedProductIds(User.UserId userId) {
        final int numberOfUsers = 5;
        final int numberOfProducts = 5;

        var userProductIds = findProductIdsByUserId(userId.value());
        var similarUsers = findSimilarUsers(userProductIds, numberOfUsers);

        return favouriteProductRepository.findRecommendedProductIds(similarUsers, userProductIds, numberOfProducts).stream()
                .map(Product.ProductId::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFavouriteProduct(User.UserId userId, Product.ProductId productId) {
        favouriteProductRepository.deleteFavouriteProduct(userId.value(), productId.value());
    }

    private List<String> findProductIdsByUserId(Long userId) {
        return favouriteProductRepository.findProductIdsByUserId(userId);

    }

    private List<Long> findSimilarUsers(List<String> userProductIds, int numberOfUsers) {
        return favouriteProductRepository.findSimilarUsers(userProductIds, numberOfUsers);
    }


}
