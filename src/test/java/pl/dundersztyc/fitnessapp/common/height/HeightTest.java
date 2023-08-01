package pl.dundersztyc.fitnessapp.common.height;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HeightTest {

    @Test
    void shouldConvertCmToOtherMeasures() {
        Height height = Height.fromCm(250);

        assertThat(height.getCm()).isEqualTo(250);
        assertThat(height.getM()).isEqualTo(2.5);
        assertThat(height.getInches()).isEqualTo(98);
    }

    @Test
    void shouldConvertMetersToOtherMeasures() {
        Height height = Height.fromM(1.8);

        assertThat(height.getCm()).isEqualTo(180);
        assertThat(height.getM()).isEqualTo(1.8);
        assertThat(height.getInches()).isEqualTo(70);
    }

    @Test
    void shouldConvertInchesToOtherMeasures() {
        Height height = Height.fromInches(100);

        assertThat(height.getCm()).isEqualTo(254);
        assertThat(height.getM()).isEqualTo(2.54);
        assertThat(height.getInches()).isEqualTo(100);
    }

    @Test
    void shouldThrowWhenCreateHeightWithValueLessThanOrEqualTo0() {
        assertThrows(ConstraintViolationException.class, () -> {Height.fromCm(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Height.fromCm(0);});

        assertThrows(ConstraintViolationException.class, () -> {Height.fromM(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Height.fromM(0);});

        assertThrows(ConstraintViolationException.class, () -> {Height.fromInches(-1);});
        assertThrows(ConstraintViolationException.class, () -> {Height.fromInches(0);});
    }

}