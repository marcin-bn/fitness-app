package pl.dundersztyc.fitnessapp.favouritefoods.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface FavouriteProductRepository extends JpaRepository<FavouriteProductJpaEntity, Long> {
    Optional<List<FavouriteProductJpaEntity>> findByUserId(Long userId);

    @Query("""
            SELECT f.productId FROM FavouriteProductJpaEntity f
            WHERE f.userId = :userId
            """)
    List<String> findProductIdsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT f.userId FROM FavouriteProductJpaEntity f
            WHERE f.productId IN :userProductIds
            GROUP BY f.userId
            ORDER BY COUNT(f.productId) DESC
            LIMIT :numberOfUsers
            OFFSET 1
            """)
    List<Long> findSimilarUsers(@Param("userProductIds") List<String> userProductIds,
                                @Param("numberOfUsers") Integer numberOfUsers);


    @Query("""
            SELECT f.productId FROM FavouriteProductJpaEntity f
            WHERE f.userId IN :similarUsers
            AND f.productId NOT IN :userProductIds
            GROUP BY f.productId
            ORDER BY COUNT(f.productId) DESC
            LIMIT :numberOfProducts
            """)
    List<String> findRecommendedProductIds(@Param("similarUsers") List<Long> similarUsers,
                                           @Param("userProductIds") List<String> userProductIds,
                                           @Param("numberOfProducts") Integer numberOfProducts);

    @Modifying
    @Query("""
            DELETE FROM FavouriteProductJpaEntity f
            WHERE f.userId = :userId
            AND f.productId = :productId
            """)
    void deleteFavouriteProduct(@Param("userId") Long userId,
                                @Param("productId") String productId);
}
