package pl.dundersztyc.fitnessapp.activity.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.activity.application.port.in.ActivityRecordRequest;
import pl.dundersztyc.fitnessapp.activity.application.port.in.ActivityRequest;
import pl.dundersztyc.fitnessapp.activity.application.port.in.AddActivityUseCase;
import pl.dundersztyc.fitnessapp.activity.application.port.in.GetActivitiesUseCase;
import pl.dundersztyc.fitnessapp.activity.application.port.out.LoadActivitiesPort;
import pl.dundersztyc.fitnessapp.activity.application.port.out.SaveActivityPort;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.activity.domain.ActivityRecord;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ActivityService implements AddActivityUseCase, GetActivitiesUseCase {

    private final LoadActivitiesPort loadActivitiesPort;
    private final SaveActivityPort saveActivityPort;

    @Override
    public Activity.ActivityId addActivity(ActivityRequest activityRequest) {
        var activityRecords = activityRequest.activityRecords().stream()
                .map(this::mapToActivityRecord)
                .collect(Collectors.toList());

        var activity = Activity.withoutId(
                new User.UserId(activityRequest.userId()),
                activityRequest.activityType(),
                Weight.fromKg(activityRequest.weightInKg()),
                activityRecords
        );

        return saveActivityPort.save(activity);
    }

    @Override
    public Activity getActivityById(Activity.ActivityId activityId) {
        return loadActivitiesPort.load(activityId);
    }

    @Override
    public List<Activity> getActivitiesFromTo(User.UserId userId, LocalDateTime from, LocalDateTime to) {
        return loadActivitiesPort.loadFromTo(userId, from, to);
    }

    @Override
    public List<Activity> getLastActivities(User.UserId userId, int numberOfActivities) {
        return loadActivitiesPort.loadLast(userId, numberOfActivities);
    }

    private ActivityRecord mapToActivityRecord(ActivityRecordRequest recordRequest) {
        if (recordRequest.latitude() == null || recordRequest.longitude() == null) {
            return ActivityRecord.withoutId(null, recordRequest.heartRate(), recordRequest.timestamp());
        }
        return ActivityRecord.withoutId(new Coordinates(recordRequest.latitude(),
                recordRequest.longitude()), recordRequest.heartRate(), recordRequest.timestamp());
    }
}
