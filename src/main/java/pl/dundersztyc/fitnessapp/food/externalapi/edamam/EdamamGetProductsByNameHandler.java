package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.food.application.GetProductsByNameHandler;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.modelcreator.ModelCreator;
import pl.dundersztyc.fitnessapp.food.externalapi.edamam.model.ProductList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EdamamGetProductsByNameHandler extends GetProductsByNameHandler {

    private final EdamamGetProductByIdHandler getProductByIdHandler;
    private final EdamamUrlProvider urlProvider;

    @Override
    public List<Product> getProductsByName(String name, int numberOfProducts) {
        if (name == null || name.isEmpty()) {
            return Collections.emptyList();
        }
        String URL = urlProvider.getBasicProductParserURL() + "&ingr=" + name;
        var productList = ModelCreator.getForObject(URL, ProductList.class);
        var productIds = productList.getProductIds(numberOfProducts);

        List<Product> products = new ArrayList<>();
        productIds.forEach(
                productId -> getProductByIdHandler.getProductById(productId).ifPresent(products::add)
        );

        return products;
    }
}
