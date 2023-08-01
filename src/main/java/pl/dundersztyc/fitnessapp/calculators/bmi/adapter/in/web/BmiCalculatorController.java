package pl.dundersztyc.fitnessapp.calculators.bmi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in.CalculateBmiUseCase;
import pl.dundersztyc.fitnessapp.calculators.bmi.application.port.in.DetermineBmiCategoryUseCase;
import pl.dundersztyc.fitnessapp.calculators.bmi.domain.BmiCategory;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

@RestController
@RequestMapping("api/v1/calculators/bmi")
@RequiredArgsConstructor
public class BmiCalculatorController {

    private final CalculateBmiUseCase calculateBmiUseCase;
    private final DetermineBmiCategoryUseCase determineBmiCategoryUseCase;

    @GetMapping
    public double calculateBmi(@RequestParam("weight") double weightInKg,
                               @RequestParam("height") double heightInM) {
        return calculateBmiUseCase.calculateBmi(Weight.fromKg(weightInKg), Height.fromM(heightInM));
    }

    @GetMapping("/category")
    public BmiCategory determineBmiCategory(@RequestParam("bmi") double bmi) {
        return determineBmiCategoryUseCase.determineBmiCategory(bmi);
    }

}
