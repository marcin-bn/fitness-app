package pl.dundersztyc.fitnessapp.user.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.user.adapter.in.UserResponse;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.fromJson;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;
import static pl.dundersztyc.fitnessapp.common.UserTestData.defaultUser;

class UserControllerTest extends AbstractIntegrationTest {

    private static final String USER_URL = "/api/v1/users";
    private static final String REGISTER_URL = "/api/v1/public/register";

    @Test
    @WithMockUser
    void shouldRegisterAndGetUser() throws Exception {

        // register user
        User user = defaultUser().id(new User.UserId(1L)).firstName("firstName123").build();
        var registeredUser = register(user);
        var userId = getIdFromRegisteredUser(registeredUser.andReturn());

        // get user
        MvcResult getUserResult = getUser(userId)
                .andExpect(status().isOk())
                .andReturn();

        UserResponse userResponse = getUserResponse(getUserResult);

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.firstName()).isEqualTo("firstName123");
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenUserIdIsNotFound() throws Exception {
        getUser(1L)
                .andExpect(status().isNotFound());
    }

    private UserResponse getUserResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(result.getResponse().getContentAsString(), UserResponse.class);
    }


    private ResultActions getUser(Long userId) throws Exception {
        return mockMvc.perform(get(USER_URL + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions register(User user) throws Exception {
        return mockMvc
                .perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(user)));
    }

    private Long getIdFromRegisteredUser(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(result.getResponse().getContentAsString());
        return node.path("value").asLong();
    }

}