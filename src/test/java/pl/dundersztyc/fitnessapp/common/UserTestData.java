package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.domain.User.UserBuilder;

public class UserTestData {


    public static UserBuilder defaultUser() {
        return User.builder()
                .id(new User.UserId(1L))
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jankowalski@gmail.com")
                .username("jan123")
                .password("password1");
    }
}
