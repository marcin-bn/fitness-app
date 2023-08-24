package pl.dundersztyc.fitnessapp.elevation.externalapi.elevationapi;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.elevation.application.port.in.GetAltitudeUseCase;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;
import pl.dundersztyc.fitnessapp.elevation.externalapi.elevationapi.model.ElevationResponseList;
import pl.dundersztyc.fitnessapp.food.externalapi.common.modelcreator.ModelCreator;

public class ElevationService implements GetAltitudeUseCase {

    private final static String API_URL = "https://api.open-elevation.com/api/v1/lookup";

    @Override
    public Double getAltitude(@NonNull Coordinates coordinates) {
        String URL = API_URL + "?locations=" + coordinates.latitude() + "," + coordinates.longitude();
        var responseList = ModelCreator.getForObject(URL, ElevationResponseList.class);
        try {
            return responseList.getResults().get(0).getElevation();
        }
        catch (NullPointerException exception) {
            return null;
        }
    }
}
