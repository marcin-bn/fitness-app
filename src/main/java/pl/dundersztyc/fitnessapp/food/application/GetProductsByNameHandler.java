package pl.dundersztyc.fitnessapp.food.application;

import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler.ChainHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class GetProductsByNameHandler implements ChainHandler<GetProductsByNameHandler> {

    private GetProductsByNameHandler nextHandler;

    @Override
    public void setNextHandler(GetProductsByNameHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public List<Product> handleGetProductsByName(String name, int numberOfProductsPerHandler) {
        var handler = this;
        List<Product> products = new ArrayList<>();

        while (handler != null) {
            products.addAll(handler.getProductsByName(name, numberOfProductsPerHandler));
            handler = handler.nextHandler;
        }

        if (products.isEmpty()) {
            throw new NoSuchElementFoundException("cannot find products with given name");
        }
        return products;
    }

    public abstract List<Product> getProductsByName(String name, int numberOfProducts);
}
