package pl.dundersztyc.fitnessapp.mealplan.adapter.in.presenter;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class NutrientPresenterFactoryTest {

    @Test
    void shouldGetPremiumNutrientPresenterForPremiumOrAdminRoles() {
        var presenterForPremium = new NutrientPresenterFactory().create(
                List.of(new FakeAuthority("ROLE_USER_PREMIUM"))
        );
        var presenterForAdmin = new NutrientPresenterFactory().create(
                List.of(new FakeAuthority("ROLE_USER_ADMIN"))
        );

        assertThat(presenterForPremium instanceof PremiumNutrientPresenter).isTrue();
        assertThat(presenterForAdmin instanceof PremiumNutrientPresenter).isTrue();
    }

    @Test
    void shouldGetCommonNutrientPresenterForLoggedRoles() {
        var presenterForLogged = new NutrientPresenterFactory().create(
                List.of(new FakeAuthority("ROLE_USER_LOGGED"))
        );

        assertThat(presenterForLogged instanceof CommonNutrientPresenter).isTrue();
    }

    @Test
    void shouldThrowWhenRolesAreInvalid() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {new NutrientPresenterFactory().create(List.of(new FakeAuthority("INVALID_ROLE")));}
        );
        assertThat(exception.getMessage()).isEqualTo("user is not authenticated");
    }

    @Test
    void shouldThrowWhenNoRolesAreProvided() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {new NutrientPresenterFactory().create(Collections.emptyList());}
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