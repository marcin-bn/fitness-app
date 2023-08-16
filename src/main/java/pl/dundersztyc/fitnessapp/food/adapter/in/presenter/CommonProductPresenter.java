package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;

class CommonProductPresenter implements ProductPresenter {

    @Override
    public ProductResponse prepareView(ProductResponse productResponse) {
        productResponse.setVitaminA(null);
        productResponse.setVitaminC(null);
        productResponse.setVitaminD(null);
        productResponse.setVitaminE(null);
        productResponse.setVitaminK(null);
        productResponse.setSodium(null);
        productResponse.setCalcium(null);
        productResponse.setPotassium(null);
        productResponse.setMagnesium(null);
        productResponse.setIron(null);
        return productResponse;
    }
}
