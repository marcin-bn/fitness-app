package pl.dundersztyc.fitnessapp.food.application;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler.ConfigChainHandler;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static pl.dundersztyc.fitnessapp.common.ProductTestData.defaultProduct;

class GetProductsByNameHandlerTest {


    @Test
    void shouldGetProductsByNameWhenAllHandlersReturnProducts() {
        LinkedHashSet<GetProductsByNameHandler> handlers = new LinkedHashSet<>(
                List.of(new FakeHandler(), new FakeHandler())
        );
        var mainHandler = ConfigChainHandler.configChainHandler(handlers);

        var products = mainHandler.handleGetProductsByName("any name", 1);

        assertThat(products).hasSize(2);
    }

    @Test
    void shouldGetProductsByNameWhenSomeHandlersReturnEmptyOptional() {
        LinkedHashSet<GetProductsByNameHandler> handlers = new LinkedHashSet<>(
                List.of(new FakeHandler(), new ProductDoesNotExistFakeHandler())
        );
        var mainHandler = ConfigChainHandler.configChainHandler(handlers);

        var products = mainHandler.handleGetProductsByName("any name", 1);

        assertThat(products).hasSize(1);
    }

    @Test
    void shouldThrowWhenProductsWithGivenNameDoesNotExist() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {new ProductDoesNotExistFakeHandler().handleGetProductsByName("any name", 1);}
        );
        assertThat(exception.getMessage()).isEqualTo("cannot find products with given name");
    }


    private class FakeHandler extends GetProductsByNameHandler {

        @Override
        public List<Product> getProductsByName(String name, int numberOfProducts) {
            return List.of(defaultProduct().build());
        }
    }

    private class ProductDoesNotExistFakeHandler extends GetProductsByNameHandler {

        @Override
        public List<Product> getProductsByName(String name, int numberOfProducts) {
            return Collections.emptyList();
        }
    }

}