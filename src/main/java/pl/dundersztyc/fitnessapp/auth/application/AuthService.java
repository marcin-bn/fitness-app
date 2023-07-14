package pl.dundersztyc.fitnessapp.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import pl.dundersztyc.fitnessapp.auth.application.port.in.AuthenticationUseCase;
import pl.dundersztyc.fitnessapp.auth.application.port.in.ProvideJwtUseCase;
import pl.dundersztyc.fitnessapp.auth.application.port.in.RegisterUserUseCase;
import pl.dundersztyc.fitnessapp.auth.domain.AuthRequest;
import pl.dundersztyc.fitnessapp.user.application.port.out.SaveUserPort;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.Instant;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
public class AuthService implements RegisterUserUseCase, AuthenticationUseCase, ProvideJwtUseCase {

    private final PasswordEncoder passwordEncoder;
    private final SaveUserPort saveUserPort;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return saveUserPort.save(user);
    }

    @Override
    public Authentication authenticate(AuthRequest authRequest) throws AuthenticationException {
        return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
    }

    @Override
    public String provideJwt(Authentication authentication) throws BadCredentialsException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("invalid credentials");
        }
        UserDetails user = (UserDetails) authentication.getPrincipal();

        Instant now = Instant.now();
        long expiry = 36000L;

        var scope =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(joining(" "));

        var claims =
                JwtClaimsSet.builder()
                        .issuer("dunderszyc.pl")
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(expiry))
                        .subject(format("%s", user.getUsername()))
                        .claim("roles", scope)
                        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
