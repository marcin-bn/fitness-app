package pl.dundersztyc.fitnessapp.food.adapter.in.presenter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ProductPresenterFactory {
    public ProductPresenter create(Collection<? extends GrantedAuthority> authorities) {

        if (authorities.stream().anyMatch(a -> List.of("ROLE_USER_ADMIN", "ROLE_USER_PREMIUM").contains(a.getAuthority()))) {
            return new PremiumProductPresenter();
        }
        else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER_LOGGED"))) {
            return new CommonProductPresenter();
        }
        throw new IllegalStateException("user is not authenticated");
    }
}