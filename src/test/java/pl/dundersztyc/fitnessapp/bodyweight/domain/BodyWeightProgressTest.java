package pl.dundersztyc.fitnessapp.bodyweight.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BodyWeightProgressTest {

    @Test
    void shouldThrowWhenCreateProgressWithNegativeWeeks() {
        final long WEEKS = -1L;
        assertThrows(IllegalArgumentException.class,
                () -> new BodyWeightProgress(BigDecimal.valueOf(10L), WEEKS));
    }
}
