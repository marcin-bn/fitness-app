package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;

public class MealTestData {

    public static Meal.MealBuilder defaultMeal() {
        return Meal.builder()
                .userId(new User.UserId(1L))
                .weight(Weight.fromGrams(100))
                .productId(new Product.ProductId("abc"))
                .day(LocalDate.of(2020, 1, 1));
    }
}
