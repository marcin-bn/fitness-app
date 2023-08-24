package pl.dundersztyc.fitnessapp.activity.application.port.in;

import pl.dundersztyc.fitnessapp.activity.domain.Activity;

public interface AddActivityUseCase {
    Activity.ActivityId addActivity(ActivityRequest activityRequest);
}
