package pl.dundersztyc.fitnessapp.mealplan.domain;

import lombok.NonNull;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class MealPlanWindow {

    private List<Meal> meals;

    public MealPlanWindow(@NonNull List<Meal> meals) {
        this.meals = meals;
    }

    public MealPlanWindow(@NonNull Meal... meals) {
        this.meals = new ArrayList<>(Arrays.asList(meals));
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public List<Meal> findMealsFromDay(LocalDate day) {
        return groupByDay().get(day);
    }

    private Map<LocalDate, List<Meal>> groupByDay() {
        return meals.stream()
                .collect(groupingBy(Meal::getDay));
    }
}
