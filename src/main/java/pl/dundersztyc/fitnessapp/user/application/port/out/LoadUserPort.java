package pl.dundersztyc.fitnessapp.user.application.port.out;

import pl.dundersztyc.fitnessapp.user.domain.User;
import pl.dundersztyc.fitnessapp.user.domain.User.UserId;


public interface LoadUserPort {
    User findById(UserId userId);
    User findByUsername(String username);
}
