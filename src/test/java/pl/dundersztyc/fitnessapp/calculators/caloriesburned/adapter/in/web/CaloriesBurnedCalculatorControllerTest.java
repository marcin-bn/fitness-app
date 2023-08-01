package pl.dundersztyc.fitnessapp.calculators.caloriesburned.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CaloriesBurnedCalculatorControllerTest extends AbstractIntegrationTest {

    @Test
    @WithMockUser
    void shouldCalculateCaloriesBurned() throws Exception {

        ActivityType activity = ActivityType.CYCLING;
        long minutes = 30;
        double weight = 70.0;

        MvcResult calculateResult = mockMvc
                .perform(get("/api/v1/calculators/calories-burned")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("activity", activity.toString())
                        .param("minutes", Long.toString(minutes))
                        .param("weight", Double.toString(weight)))
                .andExpect(status().isOk())
                .andReturn();

        long caloriesBurned = Long.parseLong(calculateResult.getResponse().getContentAsString());

        assertThat(caloriesBurned).isEqualTo(281);
    }
}