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

class GetProductByIdTest extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldGetProductForLoggedUser() throws Exception {

        String id = "3017620422003";

        MvcResult getProductResult = mockMvc
                .perform(get("/api/v1/products/id/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode productResponse = new ObjectMapper().readTree(getProductResult.getResponse().getContentAsString());

        assertThat(productResponse).isNotNull();
        assertThat(productResponse.path("id").asText()).isEqualTo(id);
        assertThat(productResponse.path("sodium").isNull()).isTrue();
    }

    @Test
    @WithMockUser(roles = {Role.USER_PREMIUM})
    void shouldGetProductForPremiumUser() throws Exception {

        String id = "3017620422003";

        MvcResult getProductResult = mockMvc
                .perform(get("/api/v1/products/id/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode productResponse = new ObjectMapper().readTree(getProductResult.getResponse().getContentAsString());

        assertThat(productResponse).isNotNull();
        assertThat(productResponse.path("id").asText()).isEqualTo(id);
        assertThat(productResponse.at("/sodium/value/unit").asText()).isEqualTo("mg");
    }

    @Test
    @WithMockUser(roles = {Role.USER_LOGGED})
    void shouldReturnNotFoundWhenProductIdDoesNotExist() throws Exception {
       mockMvc.perform(get("/api/v1/products/id/" + "thisIdDoesNotExist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}