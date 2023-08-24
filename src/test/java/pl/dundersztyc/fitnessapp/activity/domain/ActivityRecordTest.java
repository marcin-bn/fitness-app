package pl.dundersztyc.fitnessapp.activity.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ActivityRecordTest {

    @Test
    void shouldThrowWhenCreateWithHeartRateSmallerThanOrEqualToZero() {
        assertThrows(ConstraintViolationException.class,
                () -> new ActivityRecord(new Coordinates(1, 2), 0L, LocalDateTime.now()));
        assertThrows(ConstraintViolationException.class,
                () -> new ActivityRecord(new Coordinates(1, 2), -1L, LocalDateTime.now()));
    }

    @Test
    void shouldThrowWhenCreateWithNullTimestamp() {
        assertThrows(ConstraintViolationException.class,
                () -> new ActivityRecord(new Coordinates(1, 2), 100L, null));
    }

}