package pl.dundersztyc.fitnessapp.user.application.port.out;

import pl.dundersztyc.fitnessapp.user.domain.User;

public interface SaveUserPort {
    boolean save(User user);
}
