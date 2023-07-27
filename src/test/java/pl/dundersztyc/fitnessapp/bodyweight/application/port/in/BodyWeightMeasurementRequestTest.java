package pl.dundersztyc.fitnessapp.bodyweight.application.port.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BodyWeightMeasurementRequestTest {

    @Test
    void shouldCreateRequest() {
        var request = new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(85.55));

        assertThat(request).isNotNull();
        assertThat(request.weight().compareTo(BigDecimal.valueOf(85.55))).isEqualTo(0);
    }

    @Test
    void shouldThrowWhenWeightIsLessThanOrEqual0() {

        assertThrows(ConstraintViolationException.class, () -> {
            var request = new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(0.00));
        });

        assertThrows(ConstraintViolationException.class, () -> {
            var request = new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(-20.55));
        });

    }

}