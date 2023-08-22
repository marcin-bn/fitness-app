package pl.dundersztyc.fitnessapp.user.application.port.out;

import pl.dundersztyc.fitnessapp.user.domain.User;

public interface SaveUserPort {
    User.UserId save(User user);
}
