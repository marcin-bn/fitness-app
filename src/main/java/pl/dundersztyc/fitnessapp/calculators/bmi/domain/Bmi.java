package pl.dundersztyc.fitnessapp.calculators.bmi.domain;

import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.common.weight.Weight;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Bmi {

    private final Weight weight;
    private final Height height;

    public Bmi(Weight weight, Height height) {
        this.weight = weight;
        this.height = height;
    }

    public double getValue() {
        return BigDecimal.valueOf(
                weight.getKg() / (height.getM() * height.getM())
        ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
