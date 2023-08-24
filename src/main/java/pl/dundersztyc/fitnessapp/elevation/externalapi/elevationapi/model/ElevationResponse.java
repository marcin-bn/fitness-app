package pl.dundersztyc.fitnessapp.elevation.externalapi.elevationapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class ElevationResponse {
    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("elevation")
    @Getter
    private Double elevation;
}
