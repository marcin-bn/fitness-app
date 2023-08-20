package pl.dundersztyc.fitnessapp.mealplan.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import pl.dundersztyc.fitnessapp.mealplan.adapter.in.MealResponse;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.MealRequest;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.UpdateMealRequest;
import pl.dundersztyc.fitnessapp.user.domain.Role;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MealTest extends AbstractMealTest {


    @Test
    @WithMockUser(roles = {Role.USER_PREMIUM})
    void shouldAddMeal() throws Exception {

        var day = LocalDate.of(2020, 1, 1);
        var mealRequest = new MealRequest(1L, "0049000051995", day, 100.0);

        MvcResult addResult = addMeal(mealRequest)
                .andExpect(status().isOk())
                .andReturn();

        MealResponse mealResponse = getMealResponse(addResult);

        assertThat(getMealSizeForDay(1L, day)).isEqualTo(1);
        assertThat(mealResponse.userId()).isEqualTo(1L);
        assertThat(mealResponse.productId()).isEqualTo("0049000051995");
    }

    @Test
    @WithMockUser(roles = {Role.USER_PREMIUM})
    void shouldAddAndUpdateMeal() throws Exception {
        // add meal
        var day = LocalDate.of(2020, 1, 1);
        var mealRequest = new MealRequest(1L, "0049000051995", day, 100.0);
        var addMeal = addMeal(mealRequest).andReturn();
        MealResponse addedMeal = getMealResponse(addMeal);
        assertThat(getMealSizeForDay(1L, day)).isEqualTo(1);

        // update meal
        var updateMealRequest = new UpdateMealRequest(addedMeal.id(), 1L, "0049000051995",
                day, 125.0);

        MvcResult updateResult = updateMeal(updateMealRequest)
                .andExpect(status().isOk())
                .andReturn();

        MealResponse updatedMeal = getMealResponse(updateResult);

        assertThat(getMealSizeForDay(1L, day)).isEqualTo(1);
        assertThat(updatedMeal.userId()).isEqualTo(1L);
        assertThat(updatedMeal.weightInGrams()).isEqualTo(125.0);
    }


    @Test
    @WithMockUser(roles = {Role.USER_PREMIUM})
    void shouldAddAndDeleteMeal() throws Exception {
        // add meal
        var day =  LocalDate.of(2020, 1, 1);
        var mealRequest = new MealRequest(1L, "0049000051995", day, 100.0);
        var addMeal = addMeal(mealRequest).andReturn();
        MealResponse addedMeal = getMealResponse(addMeal);
        assertThat(getMealSizeForDay(1L, day)).isEqualTo(1);

        // delete meal
        deleteMeal(addedMeal.id())
                .andExpect(status().isOk());
        assertThat(getMealSizeForDay(1L, day)).isEqualTo(0);
    }



}
