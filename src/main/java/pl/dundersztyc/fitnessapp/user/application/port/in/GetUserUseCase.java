package pl.dundersztyc.fitnessapp.user.application.port.in;

import pl.dundersztyc.fitnessapp.user.domain.User;

public interface GetUserUseCase {
    User getUser(User.UserId userId);
}
