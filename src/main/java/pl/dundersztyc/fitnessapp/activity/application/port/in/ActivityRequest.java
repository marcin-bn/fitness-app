package pl.dundersztyc.fitnessapp.activity.application.port.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;

import java.util.List;

public record ActivityRequest(
        @NonNull Long userId,
        @NonNull ActivityType activityType,
        @NonNull Double weightInKg,
        @NonNull List<ActivityRecordRequest> activityRecords
) {
}
