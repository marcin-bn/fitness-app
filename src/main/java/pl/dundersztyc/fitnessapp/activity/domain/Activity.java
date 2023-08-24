package pl.dundersztyc.fitnessapp.activity.domain;

import lombok.Getter;
import lombok.NonNull;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;
import pl.dundersztyc.fitnessapp.common.speed.Speed;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.elevation.application.port.in.GetAltitudeUseCase;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Activity {

    private ActivityId activityId;

    @NonNull
    private final User.UserId userId;

    @NonNull
    private final ActivityType activityType;

    @NonNull
    private final Weight weight;

    @NonNull
    private final List<ActivityRecord> activityRecords;

    private Activity(ActivityId activityId,
                    @NonNull User.UserId userId,
                    @NonNull ActivityType activityType,
                    @NonNull Weight weight,
                    @NonNull List<ActivityRecord> activityRecords) {
        this.activityId = activityId;
        this.userId = userId;
        this.activityType = activityType;
        this.weight = weight;
        this.activityRecords = activityRecords;

        activityRecords.removeAll(Collections.singleton(null));
        if (activityRecords.isEmpty()) {
            throw new IllegalArgumentException("activity records cannot be empty");
        }
    }

    public static Activity withId(
           ActivityId activityId,
           User.UserId userId,
           ActivityType activityType,
           Weight weight,
           List<ActivityRecord> activityRecords
    ) {
        return new Activity(activityId, userId, activityType, weight, activityRecords);
    }

    public static Activity withoutId(
            User.UserId userId,
            ActivityType activityType,
            Weight weight,
            List<ActivityRecord> activityRecords
    ) {
        return new Activity(null, userId, activityType, weight, activityRecords);
    }

    public record ActivityId(@NonNull Long value) {
    }

    public LocalDateTime getStartDate() {
        return activityRecords.get(0).timestamp();
    }

    public Duration calculateDuration() {
        if (activityRecords.size() < 2) {
            return Duration.ZERO;
        }
        return Duration.between(
                activityRecords.get(0).timestamp(),
                activityRecords.get(activityRecords.size() - 1).timestamp()
        );
    }

    public long calculateCaloriesBurned() {
        return activityType.calculateCaloriesBurned(calculateDuration().toMinutes(), weight);
    }

    public Long calculateAverageHeartRate() {
        return Double.valueOf(
                activityRecords.stream()
                        .map(ActivityRecord::heartRate)
                        .filter(Objects::nonNull)
                        .mapToLong(Long::longValue)
                        .average()
                        .orElseThrow(IllegalStateException::new)
        ).longValue();
    }

    public Double calculateAverageAltitude(GetAltitudeUseCase getAltitudeUseCase) {
        var coordinates = activityRecords.stream()
                .map(ActivityRecord::coordinates)
                .filter(Objects::nonNull);

        return coordinates
                .mapToDouble(getAltitudeUseCase::getAltitude)
                .average()
                .orElseThrow(IllegalStateException::new);
    }

    public Long calculateDistanceInMeters() {
        var coordinates = activityRecords.stream()
                .map(ActivityRecord::coordinates)
                .filter(Objects::nonNull)
                .toList();

        long sum = 0;
        for (int i = 0; i < coordinates.size() - 1; ++i){
            sum += coordinates.get(i).getDistanceBetween(coordinates.get(i + 1));
        }
        return sum;
    }

    public Speed getAverageSpeed() {
        return new Speed(calculateDistanceInMeters(), calculateDuration());
    }

}
