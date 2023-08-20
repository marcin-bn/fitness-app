package pl.dundersztyc.fitnessapp.common.weight;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeightTest {

    @Test
    void shouldConvertKgToOtherMeasures() {
        Weight weight = Weight.fromKg(100.0);

        assertThat(weight.getKg()).isEqualTo(100.0);
        assertThat(weight.getLbs()).isEqualTo(220.51);
        assertThat(weight.getGrams()).isEqualTo(100000.00);
    }

    @Test
    void shouldConvertLbsToOtherMeasures() {
        Weight weight = Weight.fromLbs(100.0);

        assertThat(weight.getKg()).isEqualTo(45.35);
        assertThat(weight.getLbs()).isEqualTo(100.0);
        assertThat(weight.getGrams()).isEqualTo(45350.00);
    }

    @Test
    void shouldConvertGramsToOtherMeasures() {
        Weight weight = Weight.fromGrams(100.0);

        assertThat(weight.getKg()).isEqualTo(0.1);
        assertThat(weight.getLbs()).isEqualTo(0.22);
        assertThat(weight.getGrams()).isEqualTo(100.0);
    }

    @Test
    void shouldThrowWhenCreateWeightWithValueLessThanOrEqualTo0() {
        assertThrows(ConstraintViolationException.class, () -> {Weight.fromKg(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Weight.fromKg(0);});

        assertThrows(ConstraintViolationException.class, () -> {Weight.fromLbs(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Weight.fromLbs(0);});

        assertThrows(ConstraintViolationException.class, () -> {Weight.fromGrams(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Weight.fromGrams(0);});
    }

}