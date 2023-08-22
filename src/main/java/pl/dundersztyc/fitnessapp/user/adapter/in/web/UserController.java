package pl.dundersztyc.fitnessapp.user.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dundersztyc.fitnessapp.user.adapter.in.UserResponse;
import pl.dundersztyc.fitnessapp.user.application.port.in.GetUserUseCase;
import pl.dundersztyc.fitnessapp.user.domain.User;

@RestController
@RequestMapping("api/v1/users/")
@RequiredArgsConstructor
class UserController {

    private final GetUserUseCase getUserUseCase;

    @GetMapping("{userId}")
    UserResponse getUser(@PathVariable("userId") Long userId) {
        return UserResponse.of(getUserUseCase.getUser(new User.UserId(userId)));
    }

}
