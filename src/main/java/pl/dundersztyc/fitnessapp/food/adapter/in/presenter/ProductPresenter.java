package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;

public interface ProductPresenter {
    ProductResponse prepareView(ProductResponse productResponse);
}
