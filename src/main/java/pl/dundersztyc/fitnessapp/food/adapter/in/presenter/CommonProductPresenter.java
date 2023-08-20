package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import pl.dundersztyc.fitnessapp.food.adapter.in.ProductResponse;

class CommonProductPresenter implements ProductPresenter {

    @Override
    public ProductResponse prepareView(ProductResponse productResponse) {
        var nutritionFacts = productResponse.getNutritionFacts();
        nutritionFacts.setVitaminA(null);
        nutritionFacts.setVitaminC(null);
        nutritionFacts.setVitaminD(null);
        nutritionFacts.setVitaminE(null);
        nutritionFacts.setVitaminK(null);
        nutritionFacts.setSodium(null);
        nutritionFacts.setCalcium(null);
        nutritionFacts.setPotassium(null);
        nutritionFacts.setMagnesium(null);
        nutritionFacts.setIron(null);
        return productResponse;
    }
}