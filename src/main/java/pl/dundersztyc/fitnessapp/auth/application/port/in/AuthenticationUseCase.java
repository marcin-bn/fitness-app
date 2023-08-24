package pl.dundersztyc.fitnessapp.auth.application.port.in;

import org.springframework.security.core.Authentication;

public interface AuthenticationUseCase {
    Authentication authenticate(AuthRequest authRequest);
}
