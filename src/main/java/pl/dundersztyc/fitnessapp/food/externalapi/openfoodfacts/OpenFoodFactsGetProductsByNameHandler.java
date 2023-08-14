package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.food.application.GetProductsByNameHandler;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.scraper.ProductListScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenFoodFactsGetProductsByNameHandler extends GetProductsByNameHandler {

    private final OpenFoodFactsGetProductByIdHandler getProductByIdHandler;
    private final ProductListScraper productListScraper;

    @Override
    public List<Product> getProductsByName(String name, int numberOfProducts) {
        if (name == null || name.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> productIds;
        try {
            productIds = productListScraper.getProductIds(name, numberOfProducts);
        }
        catch (IOException exception) {
            return Collections.emptyList();
        }

        List<Product> products = new ArrayList<>();
        productIds.forEach(
                productId -> getProductByIdHandler.getProductById(productId).ifPresent(products::add)
        );

        return products;
    }
}
