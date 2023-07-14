package pl.dundersztyc.fitnessapp.auth.application.port.in;

import pl.dundersztyc.fitnessapp.user.domain.User;

public interface RegisterUserUseCase {
    boolean register(User user);
}
