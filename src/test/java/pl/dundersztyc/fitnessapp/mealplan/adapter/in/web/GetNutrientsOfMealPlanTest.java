package pl.dundersztyc.fitnessapp.mealplan.adapter.in.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import pl.dundersztyc.fitnessapp.mealplan.application.port.in.MealRequest;
import pl.dundersztyc.fitnessapp.user.domain.Role;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetNutrientsOfMealPlanTest extends AbstractMealTest {

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldAddMealsAndGetNutrientsForLoggedUsers() throws Exception {
        Long userId = 1L;
        var day = LocalDate.of(2020, 1, 1);

        addMeals(
                new MealRequest(userId, "food_at830s9amds32fb8w6ufmaopzk8n", day, 100.0),
                new MealRequest(userId, "food_at830s9amds32fb8w6ufmaopzk8n", day, 100.0),
                new MealRequest(2L, "random", LocalDate.of(1000, 7, 7), 100.0)
        );


        var getNutrientsResult = mockMvc.perform(get("/api/v1/meal-plan/nutrients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId.toString())
                        .param("day", day.toString()))
                .andReturn();

        JsonNode nutritionFacts = new ObjectMapper().readTree(getNutrientsResult.getResponse().getContentAsString());

        assertThat(nutritionFacts.path("kcal").asDouble()).isEqualTo(536.0);
        assertThat(nutritionFacts.path("vitaminA").isNull()).isTrue();
    }

    @Test
    @WithMockUser(roles = {Role.USER_PREMIUM})
    void shouldAddMealsAndGetNutrientsForPremiumUsers() throws Exception {
        Long userId = 1L;
        var day = LocalDate.of(2020, 1, 1);

        addMeals(
                new MealRequest(userId, "food_at830s9amds32fb8w6ufmaopzk8n", day, 100.0),
                new MealRequest(userId, "food_at830s9amds32fb8w6ufmaopzk8n", day, 100.0),
                new MealRequest(2L, "random", LocalDate.of(1000, 7, 7), 100.0)
        );


        var getNutrientsResult = mockMvc.perform(get("/api/v1/meal-plan/nutrients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId.toString())
                        .param("day", day.toString()))
                .andReturn();

        JsonNode nutritionFacts = new ObjectMapper().readTree(getNutrientsResult.getResponse().getContentAsString());

        assertThat(nutritionFacts.path("kcal").asDouble()).isEqualTo(536.0);
        assertThat(nutritionFacts.path("vitaminA").isNull()).isFalse();
    }

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldReturnNotFoundWhenMealIsInvalid() throws Exception {
        Long userId = 1L;
        var day = LocalDate.of(2020, 1, 1);

        addMeals(
                new MealRequest(userId, "invalidId", day, 100.0),
                new MealRequest(userId, "invalidId", day, 100.0)
        );


        mockMvc.perform(get("/api/v1/meal-plan/nutrients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId.toString())
                        .param("day", day.toString()))
                .andExpect(status().isNotFound());
    }

}
