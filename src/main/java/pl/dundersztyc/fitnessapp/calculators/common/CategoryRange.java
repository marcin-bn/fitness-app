package pl.dundersztyc.fitnessapp.calculators.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryRange {

    private final double lowerLimit;
    private final double upperLimit;

    public boolean isInRange(double value) {
        return (lowerLimit <= value && value < upperLimit);
    }
}
