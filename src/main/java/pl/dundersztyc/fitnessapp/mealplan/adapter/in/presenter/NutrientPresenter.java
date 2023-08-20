package pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter;

import pl.dundersztyc.fitnessapp.food.adapter.in.NutritionFactsResponse;

public interface NutrientPresenter {
    NutritionFactsResponse prepareView(NutritionFactsResponse nutritionResponse);
}