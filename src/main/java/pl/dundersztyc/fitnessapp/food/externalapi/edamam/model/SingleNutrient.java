package pl.dundersztyc.fitnessapp.food.externalapi.edamam.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SingleNutrient {

    @JsonProperty("label")
    private String label;

    @JsonProperty("quantity")
    private Double quantity;

    @JsonProperty("unit")
    private String unit;

}
