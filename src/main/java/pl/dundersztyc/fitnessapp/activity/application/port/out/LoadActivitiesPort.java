package pl.dundersztyc.fitnessapp.activity.application.port.out;

import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface LoadActivitiesPort {
    Activity load(Activity.ActivityId activityId);
    List<Activity> loadFromTo(User.UserId userId, LocalDateTime baselineDate, LocalDateTime finishDate);
    List<Activity> loadLast(User.UserId userId, int numberOfActivities);
}
