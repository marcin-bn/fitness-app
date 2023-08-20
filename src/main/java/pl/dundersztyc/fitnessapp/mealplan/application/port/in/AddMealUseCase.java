package pl.dundersztyc.fitnessapp.mealplan.application.port.in;

import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;

public interface AddMealUseCase {
    Meal addMeal(MealRequest mealRequest);
}
