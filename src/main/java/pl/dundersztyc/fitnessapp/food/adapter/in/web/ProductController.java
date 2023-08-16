package pl.dundersztyc.fitnessapp.food.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;
import pl.dundersztyc.fitnessapp.food.adapter.in.presenter.ProductPresenter;
import pl.dundersztyc.fitnessapp.food.adapter.in.presenter.ProductPresenterFactory;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductByBarcodeUseCase;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductByIdUseCase;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductsByNameUseCase;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products/")
@RequiredArgsConstructor
class ProductController {

    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetProductByBarcodeUseCase getProductByBarcodeUseCase;
    private final GetProductsByNameUseCase getProductsByNameUseCase;

    private final ProductPresenterFactory productPresenterFactory;

    @GetMapping("id/{productId}")
    public ProductResponse getProductById(@PathVariable("productId") String productId, Authentication auth) {
        var product = getProductByIdUseCase.getProductById(productId);
        var productResponse = ProductResponse.of(product);
        ProductPresenter presenter = productPresenterFactory.create(auth.getAuthorities());
        return presenter.prepareView(productResponse);
    }

    @GetMapping("barcode/{barcode}")
    public ProductResponse getProductByBarcode(@PathVariable("barcode") String barcode, Authentication auth) {
        var product = getProductByBarcodeUseCase.getProductByBarcode(barcode);
        var productResponse = ProductResponse.of(product);
        ProductPresenter presenter = productPresenterFactory.create(auth.getAuthorities());
        return presenter.prepareView(productResponse);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> getProductsByName(@PathVariable("name") String name, Authentication auth) {
        var products = getProductsByNameUseCase.getProductsByName(name);
        var productResponseList = products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
        ProductPresenter presenter = productPresenterFactory.create(auth.getAuthorities());

        return productResponseList.stream()
                .map(presenter::prepareView)
                .collect(Collectors.toList());
    }


}
