package pl.dundersztyc.fitnessapp.auth.application.port.in;

import lombok.NonNull;

public record AuthRequest(
        @NonNull String username,
        @NonNull String password) {

}