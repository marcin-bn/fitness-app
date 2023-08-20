package pl.dundersztyc.fitnessapp.mealplan.application.port.out;

import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;

public interface DeleteMealPort {
    void deleteMeal(Meal.MealId mealId);
}
