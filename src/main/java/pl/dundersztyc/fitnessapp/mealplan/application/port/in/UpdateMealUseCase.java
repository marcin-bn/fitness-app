package pl.dundersztyc.fitnessapp.mealplan.application.port.in;

import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;

public interface UpdateMealUseCase {
    Meal updateMeal(UpdateMealRequest updateMealRequest);
}
