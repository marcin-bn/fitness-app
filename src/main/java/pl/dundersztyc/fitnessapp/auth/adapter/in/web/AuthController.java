package pl.dundersztyc.fitnessapp.auth.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dundersztyc.fitnessapp.auth.application.port.in.AuthenticationUseCase;
import pl.dundersztyc.fitnessapp.auth.application.port.in.ProvideJwtUseCase;
import pl.dundersztyc.fitnessapp.auth.application.port.in.RegisterUserUseCase;
import pl.dundersztyc.fitnessapp.auth.domain.AuthRequest;
import pl.dundersztyc.fitnessapp.auth.domain.UserView;
import pl.dundersztyc.fitnessapp.user.domain.User;


@RestController
@RequestMapping(path = "api/v1/public")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationUseCase authenticationUseCase;
    private final ProvideJwtUseCase provideJwtUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authenticationUseCase.authenticate(request);
            User user = (User) authentication.getPrincipal();
            String token = provideJwtUseCase.provideJwt(authentication);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(new UserView(user.getUsername()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid User request) {
        boolean isSaved = registerUserUseCase.register(request);
        return isSaved ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}


