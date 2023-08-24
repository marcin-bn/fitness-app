package pl.dundersztyc.fitnessapp.activity.application.port.in;

import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface GetActivitiesUseCase {
    Activity getActivityById(Activity.ActivityId activityId);
    List<Activity> getActivitiesFromTo(User.UserId userId, LocalDateTime from, LocalDateTime to);
    List<Activity> getLastActivities(User.UserId userId, int numberOfActivities);
}
