package pl.dundersztyc.fitnessapp.mealplan.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.MealTestData.defaultMeal;

class MealPlanWindowTest {

    @Test
    void shouldFindMealsFromDay() {
        var window = new MealPlanWindow();
        window.addMeal(defaultMeal().day(LocalDate.of(2020, 1, 1)).build());
        window.addMeal(defaultMeal().day(LocalDate.of(2020, 1, 1)).build());
        window.addMeal(defaultMeal().day(LocalDate.of(2010, 8, 9)).build());

        var mealsFromDay = window.findMealsFromDay(LocalDate.of(2020, 1, 1));

        assertThat(mealsFromDay).hasSize(2);
    }

}