package pl.dundersztyc.fitnessapp.mealplan.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.dundersztyc.fitnessapp.food.adapter.in.NutritionFactsResponse;
import pl.dundersztyc.fitnessapp.mealplan.adapter.in.MealResponse;
import pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter.NutrientPresenter;
import pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter.NutrientPresenterFactory;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.*;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealInfo;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/meal-plan/")
@RequiredArgsConstructor
public class MealPlanController {

    private final AddMealUseCase addMealUseCase;
    private final UpdateMealUseCase updateMealUseCase;
    private final DeleteMealUseCase deleteMealUseCase;
    private final GetMealPlanUseCase getMealPlanUseCase;
    private final GetNutrientsOfMealPlanUseCase getNutrientsOfMealPlanUseCase;

    private final NutrientPresenterFactory nutrientPresenterFactory;

    @PostMapping("meals")
    MealResponse addMeal(@RequestBody MealRequest mealRequest) {
        return MealResponse.of(addMealUseCase.addMeal(mealRequest));
    }

    @PutMapping("meals")
    MealResponse updateMeal(@RequestBody UpdateMealRequest updateMealRequest) {
        return MealResponse.of(updateMealUseCase.updateMeal(updateMealRequest));
    }

    @DeleteMapping("meals/{mealId}")
    void deleteMeal(@PathVariable("mealId") Long mealId) {
        deleteMealUseCase.deleteMeal(new Meal.MealId(mealId));
    }

    @GetMapping
    List<NutritionFactsResponse> getMealPlan(@RequestParam("userId") Long userId,
                                             @RequestParam("day") LocalDate day,
                                             Authentication auth) {
        var meals = getMealPlanUseCase.getMealsForDay(new User.UserId(userId), day);

        var mealPlan = meals.stream()
                .map(MealInfo::getNutritionFacts)
                .map(NutritionFactsResponse::of)
                .collect(Collectors.toList());
        NutrientPresenter presenter = nutrientPresenterFactory.create(auth.getAuthorities());


        return mealPlan.stream()
                .map(presenter::prepareView)
                .collect(Collectors.toList());

    }

    @GetMapping("/nutrients")
    NutritionFactsResponse getNutrientsOfMealPlan(@RequestParam("userId") Long userId,
                                                  @RequestParam("day") LocalDate day,
                                                  Authentication auth) {
        var nutrients = getNutrientsOfMealPlanUseCase.getNutrientsForDay(new User.UserId(userId), day);
        var response = NutritionFactsResponse.of(nutrients);
        NutrientPresenter presenter = nutrientPresenterFactory.create(auth.getAuthorities());
        return presenter.prepareView(response);
    }

}
