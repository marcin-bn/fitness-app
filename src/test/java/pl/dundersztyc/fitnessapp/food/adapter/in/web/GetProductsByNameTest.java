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

class GetProductsByNameTest extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldGetProductsForLoggedUser() throws Exception {

        String productName = "nutella";

        MvcResult getProductsResult = mockMvc
                .perform(get("/api/v1/products/name/" + productName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode productResponse = new ObjectMapper().readTree(getProductsResult.getResponse().getContentAsString());

        assertThat(productResponse).isNotNull();
        assertThat(productResponse).hasSize(10);
        assertThat(productResponse.get(0).path("nutritionFacts").path("sodium").isNull()).isTrue();
    }

    @Test
    @WithMockUser(roles = {Role.USER_PREMIUM})
    void shouldGetProductsForPremiumUser() throws Exception {

        String productName = "nutella";

        MvcResult getProductsResult = mockMvc
                .perform(get("/api/v1/products/name/" + productName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode productResponse = new ObjectMapper().readTree(getProductsResult.getResponse().getContentAsString());

        assertThat(productResponse).isNotNull();
        assertThat(productResponse).hasSize(10);
        assertThat(productResponse.get(0).path("nutritionFacts").at("/sodium/value/unit").asText()).isEqualTo("mg");
    }

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldReturnNotFoundWhenProductNameDoesNotExist() throws Exception {
       mockMvc.perform(get("/api/v1/products/name/" + "thisProductNameDoesNotExist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}