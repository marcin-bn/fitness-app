package pl.dundersztyc.fitnessapp.calculators.bmi.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.calculators.bmi.domain.BmiCategory;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BmiCalculatorControllerTest extends AbstractIntegrationTest {

    @Test
    @WithMockUser
    void shouldCalculateBmi() throws Exception {

        double weight = 70.9;
        double height = 1.78;

        MvcResult calculateResult = mockMvc
                .perform(get("/api/v1/calculators/bmi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("weight", Double.toString(weight))
                        .param("height", Double.toString(height)))
                .andExpect(status().isOk())
                .andReturn();

        double bmi = Double.parseDouble(calculateResult.getResponse().getContentAsString());

        assertThat(bmi).isEqualTo(22.38);
    }

    @Test
    @WithMockUser
    void shouldDetermineBmiCategory() throws Exception {

        double bmi = 22.57;

        MvcResult determineCategoryResult = mockMvc
                .perform(get("/api/v1/calculators/bmi/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bmi", Double.toString(bmi)))
                .andExpect(status().isOk())
                .andReturn();

        BmiCategory category = getBmiCategoryFromResponse(determineCategoryResult);

        assertThat(category).isEqualTo(BmiCategory.NORMAL_WEIGHT);
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestWhenWeightIsInvalid() throws Exception {

        mockMvc.perform(get("/api/v1/calculators/bmi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("weight", Double.toString(-20))
                        .param("height", Double.toString(175)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestWhenHeightIsInvalid() throws Exception {

        mockMvc.perform(get("/api/v1/calculators/bmi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("weight", Double.toString(80))
                        .param("height", Double.toString(-100)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestWhenBmiIsInvalid() throws Exception {

        mockMvc.perform(get("/api/v1/calculators/bmi/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bmi", Double.toString(-20)))
                .andExpect(status().isBadRequest());
    }

    private BmiCategory getBmiCategoryFromResponse(MvcResult result) throws UnsupportedEncodingException {
        return BmiCategory.valueOf(
                result.getResponse().getContentAsString().replaceAll("\"", "")
        );
    }
}