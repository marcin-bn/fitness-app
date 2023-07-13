package pl.dundersztyc.fitnessapp.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.dundersztyc.fitnessapp.common.UserTestData.defaultUser;

class UserTest {

    @Test
    void shouldCreateUserWithoutId() {

        User user = defaultUser()
                .id(null)
                .build();

        assertThat(user.getId()).isNull();
        assertThat(user).isNotNull();
    }

    @Test
    void shouldThrowWhenCreateUserWithIncorrectCredentials() {

        assertThrows(NullPointerException.class, () -> {
            User user = defaultUser()
                    .password(null)
                    .build();
        });
    }

    @Test
    void shouldThrowWhenSetIncorrectCredentials() {

        assertThrows(NullPointerException.class, () -> {
            User user = defaultUser().build();
            user.setPassword(null);
        });
    }

}