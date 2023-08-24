package pl.dundersztyc.fitnessapp.elevation.application.port.in;

import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;

public interface GetAltitudeUseCase {
    Double getAltitude(Coordinates coordinates);
}
