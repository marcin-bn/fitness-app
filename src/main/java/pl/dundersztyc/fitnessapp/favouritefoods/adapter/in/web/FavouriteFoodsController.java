package pl.dundersztyc.fitnessapp.favouritefoods.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dundersztyc.fitnessapp.favouritefoods.adapter.in.FavouriteProductResponse;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.AddFavouriteProductUseCase;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.DeleteFavouriteProductUseCase;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.GetFavouriteProductsUseCase;
import pl.dundersztyc.fitnessapp.favouritefoods.application.port.in.GetRecommendedProductsUseCase;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/favourite-foods")
@RequiredArgsConstructor
class FavouriteFoodsController {

    private final AddFavouriteProductUseCase addFavouriteProductUseCase;
    private final GetFavouriteProductsUseCase getFavouriteProductsUseCase;
    private final GetRecommendedProductsUseCase getRecommendedProductsUseCase;
    private final DeleteFavouriteProductUseCase deleteFavouriteProductUseCase;


    @PostMapping
    FavouriteProductResponse addFavouriteProduct(@RequestParam("userId") Long userId,
                                                 @RequestParam("productId") String productId) {
        return FavouriteProductResponse.of(
                addFavouriteProductUseCase.addFavouriteProduct(new User.UserId(userId), new Product.ProductId(productId))
        );
    }

    @GetMapping
    List<FavouriteProductResponse> getFavouriteProducts(@RequestParam("userId") Long userId) {
        return getFavouriteProductsUseCase.getFavouriteProductsByUserId(new User.UserId(userId)).stream()
                .map(FavouriteProductResponse::of)
                .collect(Collectors.toList());
    }

    @GetMapping("recommended")
    List<Product.ProductId> getRecommendedProducts(@RequestParam("userId") Long userId) {
        return getRecommendedProductsUseCase.getRecommendedProductIds(new User.UserId(userId));
    }

    @DeleteMapping
    void deleteFavouriteProduct(@RequestParam("userId") Long userId,
                                @RequestParam("productId") String productId) {
        deleteFavouriteProductUseCase.deleteFavouriteProduct(new User.UserId(userId), new Product.ProductId(productId));
    }

}
