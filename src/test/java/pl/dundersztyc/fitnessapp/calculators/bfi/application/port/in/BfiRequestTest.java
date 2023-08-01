package pl.dundersztyc.fitnessapp.calculators.bfi.application.port.in;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.common.height.Height;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BfiRequestTest {

    @Test
    void shouldCreateRequest() {
        var request = new BfiRequest(Gender.MAN, 32, 80, 90, 184);

        assertThat(request).isNotNull();
        assertThat(request.heightInCm()).isEqualTo(184);
    }

    @Test
    void shouldThrowWhenCreateRequestWithInvalidParams() {
        assertThrows(ConstraintViolationException.class, () -> {
            var request = new BfiRequest(null, 32, 80, 90, 184);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            var request = new BfiRequest(Gender.WOMAN, 32, -1, 90, 184);
        });
    }

}