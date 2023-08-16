package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class ProductPresenterFactoryTest {

    @Test
    void shouldGetPremiumProductPresenterForPremiumOrAdminRoles() {
        var presenterForPremium = new ProductPresenterFactory().create(
                List.of(new FakeAuthority("ROLE_USER_PREMIUM"))
        );
        var presenterForAdmin = new ProductPresenterFactory().create(
                List.of(new FakeAuthority("ROLE_USER_ADMIN"))
        );

        assertThat(presenterForPremium instanceof PremiumProductPresenter).isTrue();
        assertThat(presenterForAdmin instanceof PremiumProductPresenter).isTrue();
    }

    @Test
    void shouldGetCommonProductPresenterForLoggedRoles() {
        var presenterForPremium = new ProductPresenterFactory().create(
                List.of(new FakeAuthority("ROLE_USER_LOGGED"))
        );

        assertThat(presenterForPremium instanceof CommonProductPresenter).isTrue();
    }

    @Test
    void shouldThrowWhenRolesAreInvalid() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {new ProductPresenterFactory().create(List.of(new FakeAuthority("INVALID_ROLE")));}
        );
        assertThat(exception.getMessage()).isEqualTo("user is not authenticated");
    }

    @Test
    void shouldThrowWhenNoRolesAreProvided() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {new ProductPresenterFactory().create(Collections.emptyList());}
        );
        assertThat(exception.getMessage()).isEqualTo("user is not authenticated");
    }


    @RequiredArgsConstructor
    private class FakeAuthority implements GrantedAuthority {

        private final String authority;

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}