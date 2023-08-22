package pl.dundersztyc.fitnessapp.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.user.application.port.in.GetUserUseCase;
import pl.dundersztyc.fitnessapp.user.application.port.out.LoadUserPort;
import pl.dundersztyc.fitnessapp.user.domain.User;

@Service
@RequiredArgsConstructor
class UserService implements GetUserUseCase {

    private final LoadUserPort loadUserPort;

    @Override
    public User getUser(User.UserId userId) {
        return loadUserPort.findById(userId);
    }
}
