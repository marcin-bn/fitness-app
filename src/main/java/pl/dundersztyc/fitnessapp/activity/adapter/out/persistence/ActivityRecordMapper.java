package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.activity.domain.ActivityRecord;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
class ActivityRecordMapper {

    public List<ActivityRecord> mapToDomainEntities(List<ActivityRecordJpaEntity> activityRecords) {
        return activityRecords.stream()
                .sorted(Comparator.comparing(ActivityRecordJpaEntity::getActivitySequenceNumber))
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    public List<ActivityRecordJpaEntity> mapToJpaEntities(List<ActivityRecord> activityRecords, Activity.ActivityId activityId) {
        return IntStream.range(0, activityRecords.size())
                .mapToObj(i -> mapToJpaEntity(activityRecords.get(i), activityId, i))
                .collect(Collectors.toList());
    }

    private ActivityRecord mapToDomainEntity(ActivityRecordJpaEntity activityRecord) {
        return new ActivityRecord(
                activityRecord.getId(),
                new Coordinates(activityRecord.getLatitude(), activityRecord.getLongitude()),
                activityRecord.getHeartRate(),
                activityRecord.getTimestamp()
        );
    }

    private ActivityRecordJpaEntity mapToJpaEntity(ActivityRecord activityRecord, Activity.ActivityId activityId, long activitySequenceNumber) {
        return ActivityRecordJpaEntity.builder()
                .id(activityRecord.id())
                .activityId(activityId.value())
                .activitySequenceNumber(activitySequenceNumber)
                .latitude(activityRecord.coordinates().latitude())
                .longitude(activityRecord.coordinates().longitude())
                .heartRate(activityRecord.heartRate())
                .timestamp(activityRecord.timestamp())
                .build();
    }


}
