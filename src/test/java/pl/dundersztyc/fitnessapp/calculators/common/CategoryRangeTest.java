package pl.dundersztyc.fitnessapp.calculators.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryRangeTest {

    @Test
    void isInRange() {
        CategoryRange categoryRange = new CategoryRange(10.0, 100.0);

        assertThat(categoryRange.isInRange(10.0)).isTrue();
        assertThat(categoryRange.isInRange(50.0)).isTrue();
        assertThat(categoryRange.isInRange(100.0)).isFalse(); // Category range creates a range [lowerLimit; upperLimit)
    }
}