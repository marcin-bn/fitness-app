package pl.dundersztyc.fitnessapp.mealplan.application.port.out;

import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;

public interface LoadMealPlanProfilePort {
    MealPlanProfile load(User.UserId userId, LocalDate baselineDate, LocalDate finishDate);
}
