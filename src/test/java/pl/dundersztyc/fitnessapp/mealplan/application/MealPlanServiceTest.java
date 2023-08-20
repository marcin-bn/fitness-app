package pl.dundersztyc.fitnessapp.mealplan.application;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.dundersztyc.fitnessapp.food.application.port.in.GetProductByIdUseCase;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.MealRequest;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.UpdateMealRequest;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.DeleteMealPort;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.LoadMealPlanProfilePort;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.UpdateMealPlanProfilePort;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanProfile;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static pl.dundersztyc.fitnessapp.common.MealTestData.defaultMeal;
import static pl.dundersztyc.fitnessapp.common.ProductTestData.defaultProduct;

class MealPlanServiceTest {

    private final LoadMealPlanProfilePort loadMealPlanProfilePort = mock(LoadMealPlanProfilePort.class);
    private final GetProductByIdUseCase getProductByIdUseCase = new GivenGetProductByIdWillReturnDefaultProduct();
    private final DeleteMealPort deleteMealPort = mock(DeleteMealPort.class);
    private final UpdateMealPlanProfilePort updateMealPlanProfilePort = mock(UpdateMealPlanProfilePort.class);
    private final MealPlanService mealPlanService =
            new MealPlanService(loadMealPlanProfilePort, getProductByIdUseCase, deleteMealPort, updateMealPlanProfilePort);

    @Test
    void shouldAddMeal() {
        MealRequest mealRequest = new MealRequest(1L, "productId", LocalDate.of(2018, 1, 1), 100.0);
        User.UserId userId = new User.UserId(1L);

        givenUpdateWillReturnIds(List.of(new Meal.MealId(1L)));

        mealPlanService.addMeal(mealRequest);

        thenMealHasBeenAdded(mealRequest);
    }

    @Test
    void shouldUpdateMeal() {
        UpdateMealRequest updateMealRequest =
                new UpdateMealRequest(1L, 1L, "productId", LocalDate.of(2018, 1, 1), 100.0);
        User.UserId userId = new User.UserId(1L);

        mealPlanService.updateMeal(updateMealRequest);

        thenMealHasBeenUpdated(updateMealRequest);
    }

    @Test
    void shouldDeleteMeal() {
        Meal.MealId mealId = new Meal.MealId(1L);

        mealPlanService.deleteMeal(mealId);

        thenMealHasBeenDeleted(mealId);
    }

    @Test
    void shouldGetMealsForDay() {
        LocalDate day = LocalDate.of(2020, 1, 1);
        List<Meal> meals = List.of(
                defaultMeal().id(new Meal.MealId(1L)).day(day).build(),
                defaultMeal().id(new Meal.MealId(2L)).day(day).build()
        );

        givenProfileWithMealsForDay(meals, day);

        var mealsForDay = mealPlanService.getMealsForDay(new User.UserId(1L), day);

        assertThat(mealsForDay).hasSize(2);
    }

    @Test
    void shouldGetNutrientsForDay() {
        LocalDate day = LocalDate.of(2020, 1, 1);
        List<Meal> meals = List.of(
                defaultMeal().id(new Meal.MealId(1L)).day(day).build(),
                defaultMeal().id(new Meal.MealId(2L)).day(day).build()
        );

        givenProfileWithMealsForDay(meals, day);

        var nutrients = mealPlanService.getNutrientsForDay(new User.UserId(1L), day);

        assertThat(nutrients).isNotNull();
        assertThat(nutrients.getKcal()).isEqualTo(1000);
    }


    private void thenMealHasBeenAdded(MealRequest mealRequest) {
        ArgumentCaptor<MealPlanProfile> profileCaptor = ArgumentCaptor.forClass(MealPlanProfile.class);
        then(updateMealPlanProfilePort).should(times(1))
                .updateMeals(profileCaptor.capture());

        MealPlanProfile capturedProfile = profileCaptor.getValue();
        List<Meal> meals = capturedProfile.getMealPlanWindow().getMeals();
        Meal meal = meals.get(0);

        assertThat(meals).hasSize(1);
        assertThat(meal.getUserId()).isEqualTo(new User.UserId(mealRequest.userId()));
        assertThat(meal.getWeight().getGrams()).isEqualTo(mealRequest.weightInGrams());
    }

    private void thenMealHasBeenUpdated(UpdateMealRequest updateMealRequest) {
        ArgumentCaptor<MealPlanProfile> profileCaptor = ArgumentCaptor.forClass(MealPlanProfile.class);
        then(updateMealPlanProfilePort).should(times(1))
                .updateMeals(profileCaptor.capture());

        MealPlanProfile capturedProfile = profileCaptor.getValue();
        List<Meal> meals = capturedProfile.getMealPlanWindow().getMeals();
        Meal meal = meals.get(0);

        assertThat(meals).hasSize(1);
        assertThat(meal.getId().value()).isEqualTo(updateMealRequest.userId());
        assertThat(meal.getUserId()).isEqualTo(new User.UserId(updateMealRequest.userId()));
        assertThat(meal.getWeight().getGrams()).isEqualTo(updateMealRequest.weightInGrams());
    }

    private void thenMealHasBeenDeleted(Meal.MealId mealId) {
        ArgumentCaptor<Meal.MealId> mealIdCaptor = ArgumentCaptor.forClass(Meal.MealId.class);
        then(deleteMealPort).should(times(1))
                .deleteMeal(mealIdCaptor.capture());

        Meal.MealId capturedMealId = mealIdCaptor.getValue();
        assertThat(mealId.value()).isEqualTo(capturedMealId.value());
    }

    private void givenUpdateWillReturnIds(List<Meal.MealId> ids) {
        given(updateMealPlanProfilePort.updateMeals(any(MealPlanProfile.class)))
                .willReturn(ids);
    }

    private void givenProfileWithMealsForDay(List<Meal> meals, LocalDate day) {
        MealPlanProfile profile = new MealPlanProfile(new User.UserId(1L), new MealPlanWindow(meals));

        given(loadMealPlanProfilePort.load(any(User.UserId.class), eq(day), eq(day)))
                .willReturn(profile);
    }



    private class GivenGetProductByIdWillReturnDefaultProduct implements GetProductByIdUseCase {

        @Override
        public Product getProductById(String productId) {
            return defaultProduct().build();
        }
    }

}