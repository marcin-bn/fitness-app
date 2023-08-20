package pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter;

import pl.dundersztyc.fitnessapp.food.adapter.in.NutritionFactsResponse;

class PremiumNutrientPresenter implements NutrientPresenter {

    @Override
    public NutritionFactsResponse prepareView(NutritionFactsResponse nutritionResponse) {
        return nutritionResponse;
    }
}