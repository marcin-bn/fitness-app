package pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter;

import pl.dundersztyc.fitnessapp.food.adapter.in.NutritionFactsResponse;

class CommonNutrientPresenter implements NutrientPresenter {

    @Override
    public NutritionFactsResponse prepareView(NutritionFactsResponse nutritionResponse) {
        nutritionResponse.setVitaminA(null);
        nutritionResponse.setVitaminC(null);
        nutritionResponse.setVitaminD(null);
        nutritionResponse.setVitaminE(null);
        nutritionResponse.setVitaminK(null);
        nutritionResponse.setSodium(null);
        nutritionResponse.setCalcium(null);
        nutritionResponse.setPotassium(null);
        nutritionResponse.setMagnesium(null);
        nutritionResponse.setIron(null);
        return nutritionResponse;
    }
}