package pl.dundersztyc.fitnessapp.auth.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.user.adapter.out.persistence.UserPersistenceAdapter;
import pl.dundersztyc.fitnessapp.user.adapter.out.persistence.UserRepository;
import pl.dundersztyc.fitnessapp.user.application.port.out.SaveUserPort;
import pl.dundersztyc.fitnessapp.user.domain.Role;
import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.mapper.UserMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.dundersztyc.fitnessapp.common.UserTestData.defaultUser;


public class AuthServiceTest extends AbstractIntegrationTest {

    @Autowired
    AuthService authService;

    @Autowired
    JwtDecoder jwtDecoder;


    private final static String USERNAME = "USERNAME123";


    @Test
    @WithMockUser(roles = {Role.USER_LOGGED, Role.USER_PREMIUM}, username = USERNAME)
    void shouldProvideJwtWhenUserIsAuthenticated() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String token = authService.provideJwt(auth);

        Jwt jwt = jwtDecoder.decode(token);
        String sub = jwt.getClaim("sub");
        String roles = jwt.getClaim("roles");

        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();
        assertThat(sub).isEqualTo(USERNAME);
        assertThat(roles).isEqualTo("ROLE_USER_LOGGED ROLE_USER_PREMIUM");
    }

    @Test
    void shouldThrowWhenProvideJwtWithNullAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThrows(BadCredentialsException.class, () -> {
            String token = authService.provideJwt(auth);
        });

    }

    @Test
    @WithMockUser(roles = Role.USER_LOGGED)
    void shouldThrowWhenProvideJwtWithInvalidAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);

        assertThrows(BadCredentialsException.class, () -> {
            String token = authService.provideJwt(auth);
        });

    }
}
