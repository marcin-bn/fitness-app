package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.user.domain.Role;
import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.domain.User.UserBuilder;

import java.util.Set;

public class UserTestData {


    public static UserBuilder defaultUser() {
        return User.builder()
                .id(new User.UserId(1L))
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jankowalski@gmail.com")
                .username("jan123")
                .password("password1")
                .authorities(Set.of(new Role(Role.USER_LOGGED)));
    }
}
