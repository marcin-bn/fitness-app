package pl.dundersztyc.fitnessapp.auth.adapter.in.web;

import jakarta.validation.constraints.NotNull;

record UserView(@NotNull String username)  {
}
