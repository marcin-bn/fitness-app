package pl.dundersztyc.fitnessapp.activity.adapter.in;

import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.activity.domain.ActivityRecord;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;
import pl.dundersztyc.fitnessapp.elevation.externalapi.elevationapi.ElevationService;

import java.time.LocalDateTime;
import java.util.List;

public record ActivityResponse(
    long activityId,
    long userId,
    ActivityType activityType,
    double weightInKg,
    LocalDateTime startDate,
    long durationInMinutes,
    long caloriesBurned,
    long averageHeartRate,
    double averageAltitude,
    long distanceInMeters,
    double averageSpeedInKmPerHour,
    List<ActivityRecord> activityRecords
) {

    public static ActivityResponse of(Activity activity) {
        return new ActivityResponse(
                activity.getActivityId().value(),
                activity.getUserId().value(),
                activity.getActivityType(),
                activity.getWeight().getKg(),
                activity.getStartDate(),
                activity.calculateDuration().toMinutes(),
                activity.calculateCaloriesBurned(),
                activity.calculateAverageHeartRate(),
                activity.calculateAverageAltitude(new ElevationService()),
                activity.calculateDistanceInMeters(),
                activity.getAverageSpeed().getKilometersPerHour(),
                activity.getActivityRecords()
        );
    }
}
