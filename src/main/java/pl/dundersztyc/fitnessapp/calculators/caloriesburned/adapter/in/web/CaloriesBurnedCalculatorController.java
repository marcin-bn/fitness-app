package pl.dundersztyc.fitnessapp.calculators.caloriesburned.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.application.port.in.CalculateCaloriesBurnedUseCase;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

@RestController
@RequestMapping("api/v1/calculators/calories-burned")
@RequiredArgsConstructor
public class CaloriesBurnedCalculatorController {

    private final CalculateCaloriesBurnedUseCase calculateCaloriesBurnedUseCase;

    @GetMapping
    public long calculateCaloriesBurned(@RequestParam("activity") ActivityType activity,
                                          @RequestParam("minutes") long minutes,
                                          @RequestParam("weight") double weightInKg) {
        return calculateCaloriesBurnedUseCase.calculateCaloriesBurned(activity, minutes, Weight.fromKg(weightInKg));
    }
}
