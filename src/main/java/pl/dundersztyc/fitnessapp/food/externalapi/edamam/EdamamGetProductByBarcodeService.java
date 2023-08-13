package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductByBarcodeUseCase;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.modelcreator.ModelCreator;
import pl.dundersztyc.fitnessapp.food.externalapi.edamam.model.ProductParserData;

@Service
@RequiredArgsConstructor
public class EdamamGetProductByBarcodeService implements GetProductByBarcodeUseCase {

    private final EdamamUrlProvider urlProvider;
    private final EdamamGetProductByIdHandler getProductByIdHandler;

    @Override
    public Product getProductByBarcode(String barcode) {
        String URL = urlProvider.getBasicProductParserURL() + "&upc=" + barcode;
        ProductParserData productParserData;
        try {
            productParserData = ModelCreator.getForObject(URL, ProductParserData.class);
        }
        catch (HttpClientErrorException exception) {
            throw new IllegalArgumentException("cannot find product with given barcode");
        }

        String productId = extractIdFromResponse(productParserData);
        var product = getProductByIdHandler.getProductById(productId);

        assert product.isPresent();

        return product.get();
    }

    private String extractIdFromResponse(ProductParserData response) {
        return response.getFoodId();
    }
}
