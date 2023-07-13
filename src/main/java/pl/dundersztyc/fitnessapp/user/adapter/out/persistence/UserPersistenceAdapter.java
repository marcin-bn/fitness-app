package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.user.application.port.out.LoadUserPort;
import pl.dundersztyc.fitnessapp.user.application.port.out.SaveUserPort;
import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.domain.User.UserId;
import pl.dundersztyc.fitnessapp.user.mapper.UserMapper;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

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
    public boolean save(User user) {
        if (loginCredentialsExists(user.getUsername(), user.getEmail())) return false;
        UserJpaEntity userEntity = userMapper.mapToJpaEntity(user);
        userRepository.save(userEntity);
        return true;
    }

    private boolean loginCredentialsExists(String username, String email) {
       return (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent());
    }
}
