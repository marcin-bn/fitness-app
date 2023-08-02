package pl.dundersztyc.fitnessapp.calculators.caloricneeds.application.port.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.ActivityFrequency;
import pl.dundersztyc.fitnessapp.calculators.caloricneeds.domain.BodyWeightGoal;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CaloricNeedsRequestTest {

    @Test
    void shouldCreateRequest() {
        var request = new CaloricNeedsRequest(20, Gender.WOMAN, 168, 50.5, ActivityFrequency.ACTIVE, BodyWeightGoal.MILD_WEIGHT_GAIN);

        assertThat(request).isNotNull();
        assertThat(request.heightInCm()).isEqualTo(168);
    }

    @Test
    void shouldThrowWhenCreateRequestWithInvalidParams() {
        assertThrows(ConstraintViolationException.class, () -> {
            new CaloricNeedsRequest(20, Gender.WOMAN, -20, 50.5, ActivityFrequency.ACTIVE, BodyWeightGoal.MILD_WEIGHT_GAIN);
        });

        assertThrows(ConstraintViolationException.class, () -> {
           new CaloricNeedsRequest(20, null, 168, 50.5, ActivityFrequency.ACTIVE, BodyWeightGoal.MILD_WEIGHT_GAIN);
        });
    }

}