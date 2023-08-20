package pl.dundersztyc.fitnessapp.mealplan.application.port.out;

import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanProfile;

import java.util.List;

public interface UpdateMealPlanProfilePort {
    List<Meal.MealId> updateMeals(MealPlanProfile profile);
}
