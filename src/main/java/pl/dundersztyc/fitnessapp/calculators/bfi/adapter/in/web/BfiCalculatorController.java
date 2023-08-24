package pl.dundersztyc.fitnessapp.calculators.bfi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in.BfiRequest;
import pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in.CalculateBfiUseCase;
import pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in.DetermineBfiCategoryUseCase;
import pl.dundersztyc.fitnessapp.calculators.bfi.domain.BfiCategory;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

@RestController
@RequestMapping("api/v1/calculators/bfi")
@RequiredArgsConstructor
class BfiCalculatorController {

    private final CalculateBfiUseCase calculateBfiUseCase;
    private final DetermineBfiCategoryUseCase determineBfiCategoryUseCase;


    @GetMapping
    public double calculateBfi(@RequestBody BfiRequest request) {
        return calculateBfiUseCase.calculateBfi(request.gender(), request.neckCirc(), request.waistCirc(), request.hipCirc(), Height.fromCm(request.heightInCm()));
    }

    @GetMapping("/category")
    public BfiCategory determineBfiCategory(@RequestParam("bfi") double bfi,
                                            @RequestParam("gender") Gender gender) {
        return determineBfiCategoryUseCase.determineBfiCategory(bfi, gender);
    }
}
