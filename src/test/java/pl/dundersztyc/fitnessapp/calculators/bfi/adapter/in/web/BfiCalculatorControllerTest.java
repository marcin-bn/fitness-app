package pl.dundersztyc.fitnessapp.calculators.bfi.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in.BfiRequest;
import pl.dundersztyc.fitnessapp.calculators.bfi.domain.BfiCategory;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;

class BfiCalculatorControllerTest extends AbstractIntegrationTest {

    @Test
    @WithMockUser
    void shouldCalculateBfi() throws Exception {

        var bfiRequest = new BfiRequest(Gender.WOMAN, 27, 70, 90, 167);

        MvcResult calculateResult = mockMvc
                .perform(get("/api/v1/calculators/bfi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(bfiRequest)))
                .andExpect(status().isOk())
                .andReturn();

        double bfi = Double.parseDouble(calculateResult.getResponse().getContentAsString());

        assertThat(bfi).isEqualTo(24.33);
    }

    @Test
    @WithMockUser
    void shouldDetermineBmiCategory() throws Exception {

        double bfi = 22.57;
        Gender gender = Gender.WOMAN;

        MvcResult determineCategoryResult = mockMvc
                .perform(get("/api/v1/calculators/bfi/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bfi", Double.toString(bfi))
                        .param("gender", gender.toString()))
                .andExpect(status().isOk())
                .andReturn();

        BfiCategory category = getBfiCategoryFromResponse(determineCategoryResult);

        assertThat(category).isEqualTo(BfiCategory.FITNESS);
    }

    private BfiCategory getBfiCategoryFromResponse(MvcResult result) throws UnsupportedEncodingException {
        return BfiCategory.valueOf(
                result.getResponse().getContentAsString().replaceAll("\"", "")
        );
    }

}