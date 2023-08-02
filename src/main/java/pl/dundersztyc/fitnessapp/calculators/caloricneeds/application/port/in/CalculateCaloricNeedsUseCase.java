package pl.dundersztyc.fitnessapp.calculators.caloricneeds.application.port.in;

import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.ActivityFrequency;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.BodyWeightGoal;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

public interface CalculateCaloricNeedsUseCase {
    long calculateCaloricNeeds(long age, Gender gender, Height height, Weight weight, ActivityFrequency frequency, BodyWeightGoal weightGoal);
}
