package pl.dundersztyc.fitnessapp.elevation.externalapi.elevationapi;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ElevationServiceTest {

    private final ElevationService elevationService = new ElevationService();

    @Test
    void shouldGetAltitude() {
        var coordinates = new Coordinates(10.123, 20.456);

        var altitude = elevationService.getAltitude(coordinates);

        assertThat(altitude).isEqualTo(414.0);
    }

}