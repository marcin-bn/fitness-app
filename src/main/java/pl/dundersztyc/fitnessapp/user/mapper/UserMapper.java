package pl.dundersztyc.fitnessapp.user.mapper;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.user.adapter.out.persistence.UserJpaEntity;
import pl.dundersztyc.fitnessapp.user.domain.User;

@Component
public class UserMapper {

    public User mapToDomainEntity(UserJpaEntity user) {
        return User.withId(
                new User.UserId(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public UserJpaEntity mapToJpaEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId().value())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
