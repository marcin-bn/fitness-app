package pl.dundersztyc.fitnessapp.calculators.caloriesburned.application.port.in;

import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

public interface CalculateCaloriesBurnedUseCase {
    long calculateCaloriesBurned(ActivityType activityType, long minutes, Weight weight);
}
