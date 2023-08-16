package pl.dundersztyc.fitnessapp.food.application;

import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler.ChainHandler;

import java.util.Optional;

public abstract class GetProductByIdHandler implements ChainHandler<GetProductByIdHandler> {

    private GetProductByIdHandler nextHandler;

    @Override
    public void setNextHandler(GetProductByIdHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public Product handleGetProductById(String productId) {
        if (canHandle(productId)) {
            return getProductById(productId)
                    .orElseThrow(() -> new NoSuchElementFoundException("cannot find product with given id"));
        }
        else if (nextHandler != null) {
            return nextHandler.handleGetProductById(productId);
        }
        throw new IllegalArgumentException("product id is invalid");
    }

    public abstract Optional<Product> getProductById(String productId);
    protected abstract boolean canHandle(String productId);

}
