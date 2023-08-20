package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;

class PremiumProductPresenter implements ProductPresenter {

    @Override
    public ProductResponse prepareView(ProductResponse productResponse) {
        return productResponse;
    }
}