package pl.dundersztyc.fitnessapp.stepcounter.application.port.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StepMeasurementRequestTest {

    @Test
    void shouldCreateRequest() {
        var request = new StepMeasurementRequest(1L, 1000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now());

        assertThat(request).isNotNull();
        assertThat(request.steps()).isEqualTo(1000L);
    }

    @Test
    void shouldThrowWhenStepsAreLessThan0() {

        assertThrows(ConstraintViolationException.class, () -> {
            var request = new StepMeasurementRequest(1L, -20L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now());
        });

    }
}