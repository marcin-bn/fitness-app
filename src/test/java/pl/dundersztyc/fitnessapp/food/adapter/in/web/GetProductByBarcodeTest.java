package pl.dundersztyc.fitnessapp.food.adapter.in.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.user.domain.Role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetProductByBarcodeTest extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldGetProductForLoggedUser() throws Exception {

        String barcode = "3017620422003";

        MvcResult getProductResult = mockMvc
                .perform(get("/api/v1/products/barcode/" + barcode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode productResponse = new ObjectMapper().readTree(getProductResult.getResponse().getContentAsString());

        assertThat(productResponse).isNotNull();
        assertThat(productResponse.path("nutritionFacts").path("sodium").isNull()).isTrue();
    }

    @Test
    @WithMockUser(roles = {Role.USER_PREMIUM})
    void shouldGetProductForPremiumUser() throws Exception {

        String barcode = "3017620422003";

        MvcResult getProductResult = mockMvc
                .perform(get("/api/v1/products/barcode/" + barcode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode productResponse = new ObjectMapper().readTree(getProductResult.getResponse().getContentAsString());

        assertThat(productResponse).isNotNull();
        assertThat(productResponse.path("nutritionFacts").at("/sodium/value/unit").asText()).isEqualTo("mg");
    }

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldReturnNotFoundWhenBarcodeDoesNotExist() throws Exception {
       mockMvc.perform(get("/api/v1/products/barcode/" + "thisBarcodeDoesNotExist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}