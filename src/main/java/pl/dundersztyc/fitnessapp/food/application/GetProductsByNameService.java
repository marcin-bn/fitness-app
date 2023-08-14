package pl.dundersztyc.fitnessapp.food.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductsByNameUseCase;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler.ConfigChainHandler;
import pl.dundersztyc.fitnessapp.food.externalapi.edamam.EdamamGetProductsByNameHandler;
import pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.OpenFoodFactsGetProductsByNameHandler;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductsByNameService implements GetProductsByNameUseCase {

    private final static int NUMBER_OF_PRODUCTS_PER_HANDLER = 5;

    private final EdamamGetProductsByNameHandler edamamHandler;
    private final OpenFoodFactsGetProductsByNameHandler openFoodFactsHandler;

    @Override
    public List<Product> getProductsByName(String name) {
        LinkedHashSet<GetProductsByNameHandler> handlers = new LinkedHashSet<>();
        handlers.add(edamamHandler);
        handlers.add(openFoodFactsHandler);
        GetProductsByNameHandler mainHandler = ConfigChainHandler.configChainHandler(handlers);

        return mainHandler.handleGetProductsByName(name, NUMBER_OF_PRODUCTS_PER_HANDLER);
    }
}
