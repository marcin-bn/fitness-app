package pl.dundersztyc.fitnessapp.favouritefoods.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.favouritefoods.adapter.in.FavouriteProductResponse;
import pl.dundersztyc.fitnessapp.food.domain.Product;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.fromJson;

class FavouriteFoodsControllerTest extends AbstractIntegrationTest {

    private static final String FAVOURITE_FOODS_URL = "/api/v1/favourite-foods";

    @Test
    @WithMockUser
    void shouldAddFavouriteProduct() throws Exception {

        var userId = 1L;
        var productId = "productId123";

        MvcResult addResult = addFavouriteProduct(userId, productId)
                .andExpect(status().isOk())
                .andReturn();

        FavouriteProductResponse favouriteProductResponse = getFavouriteProductResponse(addResult);

        assertThat(favouriteProductResponse.userId()).isEqualTo(1L);
        assertThat(favouriteProductResponse.productId()).isEqualTo("productId123");
    }

    @Test
    @WithMockUser
    void shouldAddAndGetFavouriteProducts() throws Exception {

        addFavouriteProducts(
            Pair.of(1L, "product1"),
            Pair.of(1L, "product2"),
            Pair.of(1L, "product3")
        );

        List<FavouriteProductResponse> favouriteProducts = getFavouriteProducts(1L);

        assertThat(favouriteProducts).hasSize(3);
    }

    @Test
    @WithMockUser
    void shouldAddAndGetRecommendedProducts() throws Exception {

        addFavouriteProducts(
                Pair.of(1L, "1"),

                Pair.of(2L, "1"),
                Pair.of(2L, "2"),
                Pair.of(2L, "3"),

                Pair.of(3L, "1"),
                Pair.of(3L, "2"),
                Pair.of(3L, "3")
        );

        MvcResult getResult = mockMvc
                .perform(get(FAVOURITE_FOODS_URL + "/recommended")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andReturn();

        var recommendedProducts = getRecommendedProductsAsList(getResult);

        assertThat(recommendedProducts).hasSize(2);
        assertThat(recommendedProducts.stream().map(Product.ProductId::value).collect(Collectors.toList()))
                .isEqualTo(List.of("2", "3"));
    }

    @Test
    @WithMockUser
    void shouldAddAndDeleteFavouriteProduct() throws Exception {

        addFavouriteProduct(1L, "productId123");

        assertThat(getFavouriteProducts(1L)).hasSize(1);

        mockMvc.perform(delete(FAVOURITE_FOODS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", String.valueOf(1L))
                        .param("productId", "productId123"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(getFavouriteProducts(1L)).hasSize(0);
    }


    private List<FavouriteProductResponse> getFavouriteProducts(Long userId) throws Exception {
        MvcResult getResult = mockMvc
                .perform(get(FAVOURITE_FOODS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andReturn();

        return getFavouriteProductResponseAsList(getResult);
    }


    @SafeVarargs
    private void addFavouriteProducts(Pair<Long, String>... requests) throws Exception {
        for (var request : requests) {
            addFavouriteProduct(request.getFirst(), request.getSecond());
        }
    }

    private ResultActions addFavouriteProduct(Long userId, String productId) throws Exception {
        return mockMvc.perform(post(FAVOURITE_FOODS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", String.valueOf(userId))
                .param("productId", productId));
    }

    private FavouriteProductResponse getFavouriteProductResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(result.getResponse().getContentAsString(), FavouriteProductResponse.class);
    }

    private List<FavouriteProductResponse> getFavouriteProductResponseAsList(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(
                result.getResponse().getContentAsString(),
                new TypeReference<List<FavouriteProductResponse>>(){}
        );
    }

    private List<Product.ProductId> getRecommendedProductsAsList(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Product.ProductId>>(){}
        );
    }

}