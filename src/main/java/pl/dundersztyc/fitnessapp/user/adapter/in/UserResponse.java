package pl.dundersztyc.fitnessapp.user.adapter.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.user.domain.Gender;
import pl.dundersztyc.fitnessapp.user.domain.User;

public record UserResponse(
        @NonNull Long id,
        @NonNull String username,
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull String email,
        @NonNull Gender gender
        ) {

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId().value(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender()
        );
    }
}
