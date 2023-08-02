package pl.dundersztyc.fitnessapp.calculators.caloricneeds.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.application.port.in.CaloricNeedsRequest;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.ActivityFrequency;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.BodyWeightGoal;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;

class CaloricNeedsControllerTest extends AbstractIntegrationTest {

    @Test
    @WithMockUser
    void shouldCalculateCaloricNeeds() throws Exception {

        CaloricNeedsRequest request = new CaloricNeedsRequest(20, Gender.WOMAN, 168, 50.5,
                ActivityFrequency.ACTIVE, BodyWeightGoal.MILD_WEIGHT_GAIN);

        MvcResult calculateResult = mockMvc
                .perform(get("/api/v1/calculators/caloric-needs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(request)))
                .andExpect(status().isOk())
                .andReturn();

        long caloricNeeds = Long.parseLong(calculateResult.getResponse().getContentAsString());

        assertThat(caloricNeeds).isEqualTo(2255);
    }

}