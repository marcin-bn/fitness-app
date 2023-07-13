package pl.dundersztyc.fitnessapp.user.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class User implements UserDetails {

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
    private String username;

    @Getter
    @NonNull
    private String password;

    @NotNull
    private Set<Role> authorities;

    public static User withId(
            UserId id,
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            Set<Role> authorities) {
        return new User(id, firstName, lastName, email, username, password, authorities);
    }

    public static User withoutId(
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            Set<Role> authorities) {
        return new User(null, firstName, lastName, email, username, password, authorities);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public record UserId(@NonNull Long value) {
    }
}
