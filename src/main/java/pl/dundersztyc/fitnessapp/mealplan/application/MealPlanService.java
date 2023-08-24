package pl.dundersztyc.fitnessapp.mealplan.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductByIdUseCase;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.food.domain.ProductNutritionFacts;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.*;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.DeleteMealPort;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.LoadMealPlanProfilePort;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.UpdateMealPlanProfilePort;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealInfo;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanProfile;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class MealPlanService implements
        GetMealPlanUseCase, GetNutrientsOfMealPlanUseCase, DeleteMealUseCase, AddMealUseCase, UpdateMealUseCase {

    private final LoadMealPlanProfilePort loadMealPlanProfilePort;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final DeleteMealPort deleteMealPort;
    private final UpdateMealPlanProfilePort updateMealPlanProfilePort;

    @Override
    public List<MealInfo> getMealsForDay(User.UserId userId, LocalDate day) {
        var profile = loadMealPlanProfilePort.load(userId, day, day);
        var meals = profile.findMealsFromDay(day);

        return meals.stream()
                .map(this::fromMeal)
                .collect(Collectors.toList());
    }

    @Override
    public ProductNutritionFacts getNutrientsForDay(User.UserId userId, LocalDate day) {
        var meals = getMealsForDay(userId, day);

        BiFunction<ProductNutritionFacts, ProductNutritionFacts, ProductNutritionFacts> nutritionFactsAccumulator =
            (lhs, rhs) -> {
                if (lhs == null) return rhs;
                return lhs.add(rhs);
            };

        return meals.stream()
                .map(MealInfo::getNutritionFacts)
                .reduce(null, nutritionFactsAccumulator, ProductNutritionFacts::add);
    }

    @Override
    public void deleteMeal(Meal.MealId mealId) {
        deleteMealPort.deleteMeal(mealId);
    }

    @Override
    public Meal addMeal(MealRequest mealRequest) {
        var meal = Meal.builder()
                .userId(new User.UserId(mealRequest.userId()))
                .day(mealRequest.day())
                .productId(new Product.ProductId(mealRequest.productId()))
                .weight(Weight.fromGrams(mealRequest.weightInGrams()))
                .build();

        MealPlanProfile profile = new MealPlanProfile(new User.UserId(mealRequest.userId()), new MealPlanWindow());
        profile.addMeal(meal);

        var updatedId = updateMealPlanProfilePort.updateMeals(profile).get(0);

        return new Meal(
                updatedId,
                meal.getUserId(),
                meal.getDay(),
                meal.getProductId(),
                meal.getWeight()
        );
    }

    @Override
    public Meal updateMeal(UpdateMealRequest updateMealRequest) {
        var meal = Meal.builder()
                .id(new Meal.MealId(updateMealRequest.id()))
                .userId(new User.UserId(updateMealRequest.userId()))
                .day(updateMealRequest.day())
                .productId(new Product.ProductId(updateMealRequest.productId()))
                .weight(Weight.fromGrams(updateMealRequest.weightInGrams()))
                .build();

        MealPlanProfile profile = new MealPlanProfile(new User.UserId(updateMealRequest.userId()), new MealPlanWindow());
        profile.addMeal(meal);

        updateMealPlanProfilePort.updateMeals(profile);

        return meal;
    }

    private MealInfo fromMeal(Meal meal) {
        var product = getProductByIdUseCase.getProductById(meal.getProductId().value());
        var weight = meal.getWeight();
        return MealInfo.valueOf(meal.getId(), product, weight);
    }


}
