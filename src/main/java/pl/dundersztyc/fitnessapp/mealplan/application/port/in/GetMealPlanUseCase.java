package pl.dundersztyc.fitnessapp.mealplan.application.port.in;

import pl.dundersztyc.fitnessapp.mealplan.domain.MealInfo;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface GetMealPlanUseCase {
    List<MealInfo> getMealsForDay(User.UserId userId, LocalDate day);
}
