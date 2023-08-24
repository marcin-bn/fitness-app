package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.activity.application.port.out.LoadActivitiesPort;
import pl.dundersztyc.fitnessapp.activity.application.port.out.SaveActivityPort;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.activity.domain.ActivityRecord;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActivityPersistenceAdapter implements SaveActivityPort, LoadActivitiesPort {

    private final ActivityRepository activityRepository;
    private final ActivityRecordRepository activityRecordRepository;

    private final ActivityMapper activityMapper;
    private final ActivityRecordMapper activityRecordMapper;

    @Override
    public Activity.ActivityId save(Activity activity) {
        var activityJpaEntity = activityMapper.mapToJpaEntity(activity);
        var savedActivity = activityRepository.save(activityJpaEntity);

        List<ActivityRecord> activityRecords = activity.getActivityRecords();
        var mappedRecords = activityRecordMapper.mapToJpaEntities
                (activityRecords, new Activity.ActivityId(savedActivity.getId()));
        mappedRecords.forEach(activityRecordRepository::save);

        return new Activity.ActivityId(savedActivity.getId());
    }

    @Override
    public Activity load(Activity.ActivityId activityId) {
        var activityJpa= activityRepository.findById(activityId.value())
                .orElseThrow(EntityNotFoundException::new);
        return loadDomain(activityJpa);
    }

    @Override
    public List<Activity> loadFromTo(User.UserId userId, LocalDateTime baselineDate, LocalDateTime finishDate) {
        var activityJpaList = activityRepository.findByUserFromTo(userId.value(), baselineDate, finishDate);

        return activityJpaList.stream()
                .map(this::loadDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> loadLast(User.UserId userId, int numberOfActivities) {
        var activityJpaList = activityRepository.findLastByUser(userId.value(), numberOfActivities);

        return activityJpaList.stream()
                .map(this::loadDomain)
                .collect(Collectors.toList());
    }

    private Activity loadDomain(ActivityJpaEntity activityJpa) {
        return activityMapper.mapToDomainEntity(
                activityJpa,
                activityRecordRepository.findAllByActivityId(activityJpa.getId())
                        .orElseThrow(IllegalStateException::new)
        );
    }
}
