package pl.dundersztyc.fitnessapp.calculators.caloriesburned.application;

import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.application.port.in.CalculateCaloriesBurnedUseCase;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

@Service
class CaloriesBurnedCalculatorService implements CalculateCaloriesBurnedUseCase {

    @Override
    public long calculateCaloriesBurned(ActivityType activityType, long minutes, Weight weight) {
        return activityType.calculateCaloriesBurned(minutes, weight);
    }
}
