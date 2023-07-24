package pl.dundersztyc.fitnessapp.auth.application.port.in;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull String username,
        @NotNull String password) {

}