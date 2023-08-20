package pl.dundersztyc.fitnessapp.mealplan.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class MealPlanProfile {

    private final User.UserId userId;

    @Getter
    private final MealPlanWindow mealPlanWindow;

    public void addMeal(Meal meal) {
        mealPlanWindow.addMeal(meal);
    }

    public List<Meal> findMealsFromDay(LocalDate day) {
        return mealPlanWindow.findMealsFromDay(day);
    }
}
