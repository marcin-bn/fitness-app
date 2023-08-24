package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;

@Component
@RequiredArgsConstructor
class ActivityMapper {

    private final ActivityRecordMapper activityRecordMapper;

    public Activity mapToDomainEntity(ActivityJpaEntity activity, List<ActivityRecordJpaEntity> activityRecords) {
        var activities = activityRecordMapper.mapToDomainEntities(activityRecords);
        return Activity.withId(
                new Activity.ActivityId(activity.getId()),
                new User.UserId(activity.getUserId()),
                activity.getActivityType(),
                Weight.fromKg(activity.getWeightInKg()),
                activities
        );
    }

    public ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return ActivityJpaEntity.builder()
                .id(activity.getActivityId() == null ? null : activity.getActivityId().value())
                .userId(activity.getUserId().value())
                .activityType(activity.getActivityType())
                .weightInKg(activity.getWeight().getKg())
                .startDate(activity.getStartDate())
                .build();
    }
}
