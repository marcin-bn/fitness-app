package pl.dundersztyc.fitnessapp.food.externalapi.edamam;

import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.food.application.GetProductByIdHandler;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.externalapi.common.modelcreator.ModelCreator;
import pl.dundersztyc.fitnessapp.food.externalapi.edamam.model.ProductData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EdamamGetProductByIdHandler extends GetProductByIdHandler {


    public EdamamGetProductByIdHandler(@Qualifier("edamam") ProductMapper productMapper, EdamamUrlProvider urlProvider) {
        this.productMapper = productMapper;
        this.urlProvider = urlProvider;
    }

    private final ProductMapper productMapper;
    private final EdamamUrlProvider urlProvider;

    @Override
    public Optional<Product> getProductById(String productId) {
        var productData = ModelCreator.postForObject(
                urlProvider.getBasicProductNutrientsURL(),
                prepareProductIdRequest(productId, ProductMetrics.defaultMetrics()),
                ProductData.class
        );
        if (productData.getTotalNutrients().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(productMapper.mapToProduct(productId, productData));
    }

    @Override
    protected boolean canHandle(String productId) {
        return isProductIdValid(productId);
    }

    private boolean isProductIdValid(String productId) {
        return productId.startsWith("food_");
    }

    private JSONObject prepareProductIdRequest(String productId, ProductMetrics metrics) {
        Map<String, Object> productInfo = new HashMap<>();
        productInfo.put("quantity", metrics.quantity);
        productInfo.put("measureURI", metrics.measure.measureURI);
        productInfo.put("foodId", productId);
        Map<String, List<Map<String, Object>>> request = new HashMap<>();
        request.put("ingredients", List.of(productInfo));

        return new JSONObject(request);
    }

    private record ProductMetrics(
            @NonNull Double quantity,
            @NonNull Measures measure) {

        public enum Measures {
            GRAM("http://www.edamam.com/ontologies/edamam.owl#Measure_gram"),
            KILOGRAM("http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram"),
            POUND("http://www.edamam.com/ontologies/edamam.owl#Measure_pound");

            final String measureURI;

            Measures(String measureURI) {
                this.measureURI = measureURI;
            }
        }

        public static ProductMetrics defaultMetrics() {
            return new ProductMetrics(100.0, Measures.GRAM);
        }
    }
}
