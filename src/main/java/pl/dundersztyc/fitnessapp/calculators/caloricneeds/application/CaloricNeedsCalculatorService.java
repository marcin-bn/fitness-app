package pl.dundersztyc.fitnessapp.calculators.caloricneeds.application;

import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.application.port.in.CalculateCaloricNeedsUseCase;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.ActivityFrequency;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.Bmr;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.BodyWeightGoal;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

@Service
public class CaloricNeedsCalculatorService implements CalculateCaloricNeedsUseCase {

    @Override
    public long calculateCaloricNeeds(long age, Gender gender, Height height, Weight weight, ActivityFrequency frequency, BodyWeightGoal weightGoal) {
        long bmr = Bmr.calculateBmr(age, weight, height, gender);
        long basicCaloricNeeds = frequency.getBasicCaloricNeeds(bmr);
        return weightGoal.getCaloricNeeds(basicCaloricNeeds);
    }
}
