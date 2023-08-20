package pl.dundersztyc.fitnessapp.mealplan.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.ServletException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.food.adapter.in.NutritionFactsResponse;
import pl.dundersztyc.fitnessapp.mealplan.adapter.in.MealResponse;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.MealRequest;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.UpdateMealRequest;
import pl.dundersztyc.fitnessapp.user.domain.Role;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.fromJson;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;

public class AbstractMealTest extends AbstractIntegrationTest {

    private static final String MEAL_URL = "/api/v1/meal-plan/meals";

    protected void addMeals(MealRequest... mealRequests) throws Exception {
        for (var request : mealRequests) {
            addMeal(request);
        }
    }

    protected ResultActions addMeal(MealRequest mealRequest) throws Exception {
        return mockMvc.perform(post(MEAL_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(mealRequest)));
    }

    protected ResultActions updateMeal(UpdateMealRequest updateMealRequest) throws Exception {
        return mockMvc.perform(put(MEAL_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(updateMealRequest)));
    }

    protected ResultActions deleteMeal(Long mealId) throws Exception {
        return mockMvc.perform(delete(MEAL_URL + "/" + String.valueOf(mealId))
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected MealResponse getMealResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(result.getResponse().getContentAsString(), MealResponse.class);
    }


    protected List<NutritionFactsResponse> getNutritionFactsResponseAsList(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(
                result.getResponse().getContentAsString(),
                new TypeReference<List<NutritionFactsResponse>>(){}
        );
    }

    @WithMockUser(roles = {Role.USER_PREMIUM})
    protected int getMealSizeForDay(Long userId, LocalDate day) throws Exception {
        try {
            var meals = mockMvc.perform(get("/api/v1/meal-plan/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("userId", userId.toString())
                            .param("day", day.toString()))
                    .andReturn();
            return fromJson(meals.getResponse().getContentAsString(), List.class).size();
        }
        catch (ServletException exception) {
            return 0;
        }
    }
}
