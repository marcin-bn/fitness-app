package pl.dundersztyc.fitnessapp.auth.domain;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull String username,
        @NotNull String password) {

    public AuthRequest() {
        this(null, null);
    }
}