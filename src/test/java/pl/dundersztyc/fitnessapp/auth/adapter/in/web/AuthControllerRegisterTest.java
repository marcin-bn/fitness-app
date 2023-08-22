package pl.dundersztyc.fitnessapp.auth.adapter.in.web;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.user.application.port.out.SaveUserPort;
import pl.dundersztyc.fitnessapp.user.domain.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;
import static pl.dundersztyc.fitnessapp.common.UserTestData.defaultUser;


public class AuthControllerRegisterTest extends AbstractIntegrationTest {


    @Autowired
    private SaveUserPort saveUserPort;

    private static final String REGISTER_URL = "/api/v1/public/register";


    @Test
    public void shouldRegisterPass() throws Exception {

        User user = defaultUser().build();

        register(toJsonString(user))
                .andExpect(status().isCreated());
    }

    @Test
    public void credentialsAreIncorrect() throws Exception {

        User user = defaultUser().build();
        String jsonUser = toJsonString(user);

        JSONObject incorrectUser = new JSONObject(toJsonString(user));
        incorrectUser.put("password", null);

        register(incorrectUser.toString())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerWithSameCredentialsIsInvalid() throws Exception {

        User user = defaultUser().build();
        saveUserPort.save(user);

        register(toJsonString(user))
                .andExpect(status().isBadRequest());
    }

    private ResultActions register(String user) throws Exception {
        return mockMvc
                .perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user));
    }

}
