package pl.dundersztyc.fitnessapp.auth.domain;

import jakarta.validation.constraints.NotNull;

public record UserView(@NotNull String username)  {
}
