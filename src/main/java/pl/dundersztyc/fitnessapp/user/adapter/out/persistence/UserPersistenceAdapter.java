package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.user.application.port.out.LoadUserPort;
import pl.dundersztyc.fitnessapp.user.application.port.out.SaveUserPort;
import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.domain.User.UserId;

@Component
@RequiredArgsConstructor
class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

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
    public UserId save(User user) {
        if (loginCredentialsExists(user.getUsername(), user.getEmail())) return null;
        UserJpaEntity userEntity = userMapper.mapToJpaEntity(user);
        var userJpa = userRepository.save(userEntity);
        return new UserId(userJpa.getId());
    }

    private boolean loginCredentialsExists(String username, String email) {
       return (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent());
    }
}
