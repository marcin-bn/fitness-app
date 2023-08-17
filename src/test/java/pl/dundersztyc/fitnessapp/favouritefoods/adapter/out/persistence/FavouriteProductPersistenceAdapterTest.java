package pl.dundersztyc.fitnessapp.favouritefoods.adapter.out.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import pl.dundersztyc.fitnessapp.AbstractTestcontainers;
import pl.dundersztyc.fitnessapp.favouritefoods.domain.FavouriteProduct;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({FavouriteProductPersistenceAdapter.class, FavouriteProductMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavouriteProductPersistenceAdapterTest extends AbstractTestcontainers {

    @Autowired
    private FavouriteProductPersistenceAdapter persistenceAdapter;

    @Autowired
    private FavouriteProductRepository favouriteProductRepository;

    @BeforeAll
    static void beforeAll(@Autowired FavouriteProductRepository favouriteProductRepository) {
        favouriteProductRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        favouriteProductRepository.deleteAll();
    }

    @Test
    @Sql(scripts = "/LoadFavouriteProducts.sql")
    void shouldFindProductsByUserId() {
        var userId = new User.UserId(1L);
        var favouriteProducts = persistenceAdapter.findByUserId(userId);

        assertThat(favouriteProducts).hasSize(5);
    }

    @Test
    void shouldSaveFavouriteProduct() {
        var favouriteProduct = new FavouriteProduct(1L, new User.UserId(1L), new Product.ProductId("productId"), LocalDateTime.now());

        boolean isSaved = persistenceAdapter.save(favouriteProduct);

        assertThat(isSaved).isTrue();
        assertThat(favouriteProductRepository.count()).isEqualTo(1);
        assertThat(favouriteProductRepository.findAll().get(0).getProductId()).isEqualTo("productId");
    }

    @Test
    void shouldDeleteFavouriteProduct() {
        var favouriteProduct = new FavouriteProduct(1L, new User.UserId(1L), new Product.ProductId("productId"), LocalDateTime.now());
        favouriteProductRepository.save(new FavouriteProductMapper().mapToJpaEntity(favouriteProduct));

        assertThat(favouriteProductRepository.count()).isEqualTo(1);

        persistenceAdapter.deleteFavouriteProduct(new User.UserId(1L), new Product.ProductId("productId"));

        assertThat(favouriteProductRepository.count()).isEqualTo(0);
    }

    @Test
    @Sql(scripts = "/LoadFavouriteProducts.sql")
    void shouldFindRecommendedProductIds() {
        var userId = new User.UserId(1L);

        var recommendedProductIds = persistenceAdapter.findRecommendedProductIds(userId);

        assertThat(recommendedProductIds).hasSize(5);
        assertThat(recommendedProductIds.stream().map(Product.ProductId::value).collect(Collectors.toList()))
                .isEqualTo(List.of("15", "16", "17", "18", "19"));
    }

}