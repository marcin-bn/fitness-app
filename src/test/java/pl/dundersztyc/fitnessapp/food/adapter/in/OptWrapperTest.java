package pl.dundersztyc.fitnessapp.food.adapter.in;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OptWrapperTest {


    @Test
    void shouldReturnWhenOptionalIsNull() {
        Optional<String> optional = null;

        var wrapper = new OptWrapper<>(optional);

        assertThat(wrapper.value()).isNull();
        assertThat(wrapper.exists()).isFalse();
    }

    @Test
    void shouldReturnWhenOptionalIsEmpty() {
        Optional<String> optional = Optional.empty();

        var wrapper = new OptWrapper<>(optional);

        assertThat(wrapper.value()).isNull();
        assertThat(wrapper.exists()).isTrue();
    }

    @Test
    void shouldReturnWhenOptionalIsPresent() {
        Optional<String> optional = Optional.of("abc");

        var wrapper = new OptWrapper<>(optional);

        assertThat(wrapper.value()).isEqualTo("abc");
        assertThat(wrapper.exists()).isTrue();
    }
}