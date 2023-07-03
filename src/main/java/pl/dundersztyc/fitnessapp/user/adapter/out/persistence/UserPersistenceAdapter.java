package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.user.application.port.out.LoadUserPort;
import pl.dundersztyc.fitnessapp.user.application.port.out.UpdateUserPort;
import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.domain.User.UserId;
import pl.dundersztyc.fitnessapp.user.mapper.UserMapper;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, UpdateUserPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User findById(UserId id) {
        UserJpaEntity user = userRepository.findById(id.value())
                .orElseThrow(EntityNotFoundException::new);

        return userMapper.mapToDomainEntity(user);
    }

    @Override
    public User findByUsername(String username) {
        UserJpaEntity user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        return userMapper.mapToDomainEntity(user);
    }

    @Override
    public void update(User user) {
        UserJpaEntity userEntity = userMapper.mapToJpaEntity(user);
        userRepository.save(userEntity);
    }
}
