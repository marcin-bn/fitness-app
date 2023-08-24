package pl.dundersztyc.fitnessapp.calculators.caloricneeds.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.application.port.in.CalculateCaloricNeedsUseCase;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.application.port.in.CaloricNeedsRequest;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

@RestController
@RequestMapping("api/v1/calculators/caloric-needs")
@RequiredArgsConstructor
class CaloricNeedsController {

    private final CalculateCaloricNeedsUseCase calculateCaloricNeedsUseCase;

    @GetMapping
    public long calculateBfi(@RequestBody CaloricNeedsRequest request) {
        return calculateCaloricNeedsUseCase.calculateCaloricNeeds(
                request.age(), request.gender(), Height.fromCm(request.heightInCm()),
                Weight.fromKg(request.weightInKg()), request.activityFrequency(), request.weightGoal()
        );
    }
}
