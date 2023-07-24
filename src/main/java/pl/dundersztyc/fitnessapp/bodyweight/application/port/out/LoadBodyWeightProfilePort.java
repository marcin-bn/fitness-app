package pl.dundersztyc.fitnessapp.bodyweight.application.port.out;

import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;

public interface LoadBodyWeightProfilePort {
    BodyWeightProfile load(User.UserId userId, LocalDateTime baselineDate);
    BodyWeightProfile load(User.UserId userId, LocalDateTime baselineDate, LocalDateTime finishDate);
}
