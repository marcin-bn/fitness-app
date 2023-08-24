package pl.dundersztyc.fitnessapp.elevation.externalapi.elevationapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class ElevationResponseList {

    @JsonProperty("results")
    @Getter
    private List<ElevationResponse> results;

}
