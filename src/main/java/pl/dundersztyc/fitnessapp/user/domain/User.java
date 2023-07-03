package pl.dundersztyc.fitnessapp.user.domain;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class User {

    @Getter
    private UserId id;

    @Getter
    @NonNull
    private String firstName;

    @Getter
    @NonNull
    private String lastName;

    @Getter
    @NonNull
    private String email;

    @Getter
    @NonNull
    private String password;

    public static User withId(
            UserId id,
            String firstName,
            String lastName,
            String email,
            String password) {
        return new User(id, firstName, lastName, email, password);
    }

    public static User withoutId(
            String firstName,
            String lastName,
            String email,
            String password) {
        return new User(null, firstName, lastName, email, password);
    }



    public record UserId(@NonNull Long value) {
    }
}
