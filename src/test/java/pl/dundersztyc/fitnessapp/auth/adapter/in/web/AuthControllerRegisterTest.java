package pl.dundersztyc.fitnessapp.auth.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.AbstractTestcontainers;
import pl.dundersztyc.fitnessapp.user.adapter.out.persistence.UserRepository;
import pl.dundersztyc.fitnessapp.user.application.port.out.SaveUserPort;
import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.mapper.UserMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.fromJson;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;
import static pl.dundersztyc.fitnessapp.common.UserTestData.defaultUser;


public class AuthControllerRegisterTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SaveUserPort saveUserPort;

    private static final String REGISTER_URL = "/api/v1/public/register";

    @BeforeAll
    static void beforeAll(@Autowired UserRepository userRepository) {
        userRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldRegisterPass() throws Exception {

        User user = defaultUser().build();

        mockMvc
            .perform(post(REGISTER_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(user)))
            .andExpect(status().isCreated());
    }

    @Test
    public void credentialsAreIncorrect() throws Exception {

        User user = defaultUser().build();
        String jsonUser = toJsonString(user);

        JSONObject incorrectUser = new JSONObject(toJsonString(user));
        incorrectUser.put("password", null);

        mockMvc
            .perform(post(REGISTER_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(incorrectUser.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void registerWithSameCredentialsIsInvalid() throws Exception {

        User user = defaultUser().build();
        saveUserPort.save(user);

        mockMvc
                .perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(user)))
                .andExpect(status().isBadRequest());
    }

}
