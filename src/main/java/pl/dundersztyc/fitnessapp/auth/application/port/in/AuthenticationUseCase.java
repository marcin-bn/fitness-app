package pl.dundersztyc.fitnessapp.auth.application.port.in;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pl.dundersztyc.fitnessapp.auth.domain.AuthRequest;

public interface AuthenticationUseCase {
    Authentication authenticate(AuthRequest authRequest) throws AuthenticationException;
}