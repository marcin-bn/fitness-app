package pl.dundersztyc.fitnessapp.food.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductByIdUseCase;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler.ConfigChainHandler;
import pl.dundersztyc.fitnessapp.food.externalapi.edamam.EdamamGetProductByIdHandler;
import pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.OpenFoodFactsGetProductByIdHandler;

import java.util.LinkedHashSet;

@Service
@RequiredArgsConstructor
public class GetProductByIdService implements GetProductByIdUseCase {

    private final EdamamGetProductByIdHandler edamamHandler;
    private final OpenFoodFactsGetProductByIdHandler openFoodFactsHandler;

    @Override
    public Product getProductById(String productId) {
        LinkedHashSet<GetProductByIdHandler> handlers = new LinkedHashSet<>();
        handlers.add(edamamHandler);
        handlers.add(openFoodFactsHandler);
        GetProductByIdHandler mainHandler = ConfigChainHandler.configChainHandler(handlers);

        return mainHandler.handleGetProductById(productId);
    }
}
