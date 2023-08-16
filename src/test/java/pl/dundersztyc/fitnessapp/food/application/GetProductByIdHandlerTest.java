package pl.dundersztyc.fitnessapp.food.application;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.food.domain.Product;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GetProductByIdHandlerTest {

    @Test
    void shouldGetProductById() {
        Product product = Product.builder().id("12345").build();

        var fakeHandler = new GetProductByIdHandler() {
            @Override
            public Optional<Product> getProductById(String productId) {
                return Optional.of(product);
            }

            @Override
            protected boolean canHandle(String productId) {
                return true;
            }
        };

        assertThat(fakeHandler.handleGetProductById("12345")).isEqualTo(product);
    }

    @Test
    void shouldThrowWhenProductIdIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {new InvalidIdFakeHandler().handleGetProductById("anyId");}
        );
        assertThat(exception.getMessage()).isEqualTo("product id is invalid");
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        NoSuchElementFoundException exception = assertThrows(NoSuchElementFoundException.class,
                () -> {new ProductDoesNotExistFakeHandler().handleGetProductById("anyId");}
        );
        assertThat(exception.getMessage()).isEqualTo("cannot find product with given id");
    }




    private class InvalidIdFakeHandler extends GetProductByIdHandler {

        @Override
        public Optional<Product> getProductById(String productId) {
            return Optional.empty();
        }

        @Override
        protected boolean canHandle(String productId) {
            return false;
        }
    }

    private class ProductDoesNotExistFakeHandler extends GetProductByIdHandler {

        @Override
        public Optional<Product> getProductById(String productId) {
            return Optional.empty();
        }

        @Override
        protected boolean canHandle(String productId) {
            return true;
        }
    }

}