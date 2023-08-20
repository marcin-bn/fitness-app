package pl.dundersztyc.fitnessapp.mealplan.application.port.in;

import pl.dundersztyc.fitnessapp.food.domain.ProductNutritionFacts;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;

public interface GetNutrientsOfMealPlanUseCase {
    ProductNutritionFacts getNutrientsForDay(User.UserId userId, LocalDate day);
}
