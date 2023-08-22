package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.user.domain.Role;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class UserMapper {

    public User mapToDomainEntity(UserJpaEntity userJpaEntity) {

        Set<Role> authorities = userJpaEntity.getAuthorities().stream()
                .map(Role::new)
                .collect(Collectors.toSet());

        return User.withId(
                new User.UserId(userJpaEntity.getId()),
                userJpaEntity.getFirstName(),
                userJpaEntity.getLastName(),
                userJpaEntity.getEmail(),
                userJpaEntity.getUsername(),
                userJpaEntity.getPassword(),
                userJpaEntity.getGender(),
                authorities
        );
    }

    public UserJpaEntity mapToJpaEntity(User user) {

        return UserJpaEntity.builder()
                .id(user.getId() == null ? null : user.getId().value())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .gender(user.getGender())
                .authorities(
                        user.getAuthorities().stream()
                                .map(Role::getAuthority)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
