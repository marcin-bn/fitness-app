package pl.dundersztyc.fitnessapp.auth.application.port.in;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface ProvideJwtUseCase {
    String provideJwt(Authentication authentication) throws BadCredentialsException;
}
