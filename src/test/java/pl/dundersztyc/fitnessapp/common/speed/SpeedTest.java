package pl.dundersztyc.fitnessapp.common.speed;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SpeedTest {

    @Test
    void shouldThrowWhenCreateSpeedWithNegativeMeters() {
        assertThrows(ConstraintViolationException.class,
                () -> new Speed(-1, Duration.ZERO));
    }

    @Test
    void shouldThrowWhenCreateSpeedWithNullDuration() {
        assertThrows(ConstraintViolationException.class,
                () -> new Speed(1, null));
    }

    @Test
    void shouldGetKilometersPerHour() {
        var speed = new Speed(1500, Duration.ofMinutes(90));
        assertThat(speed.getKilometersPerHour()).isEqualTo(1);
    }

    @Test
    void shouldGetKilometersPerHourWhenDurationIsZero() {
        var speed = new Speed(1500, Duration.ZERO);
        assertThat(speed.getKilometersPerHour()).isEqualTo(0);
    }

}