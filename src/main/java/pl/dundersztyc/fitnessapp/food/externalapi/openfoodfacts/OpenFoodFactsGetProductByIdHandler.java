package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import pl.dundersztyc.fitnessapp.food.application.GetProductByIdHandler;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.modelcreator.ModelCreator;
import pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.model.ProductData;

import java.util.Optional;

@Component
public class OpenFoodFactsGetProductByIdHandler extends GetProductByIdHandler {

    public OpenFoodFactsGetProductByIdHandler(@Qualifier("openfoodfacts") ProductMapper productMapper, OpenFoodFactsUrlProvider urlProvider) {
        this.productMapper = productMapper;
        this.urlProvider = urlProvider;
    }

    private final ProductMapper productMapper;
    private final OpenFoodFactsUrlProvider urlProvider;


    @Override
    public Optional<Product> getProductById(String productId) {
        String URL = urlProvider.getProductURL() + productId;
        ProductData productData;
        try {
            productData = ModelCreator.getForObject(URL, ProductData.class);
        }
        catch (HttpClientErrorException exception) {
            return Optional.empty();
        }

        if (!productData.isConvertibleToProduct()) {
            return Optional.empty();
        }

        Product product = productMapper.mapToProduct(productId, productData);
        return Optional.of(product);
    }

    @Override
    protected boolean canHandle(String productId) {
        return isProductIdValid(productId);
    }

    private boolean isProductIdValid(String productId) {
        return !productId.startsWith("food_");
    }
}
