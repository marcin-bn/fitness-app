package pl.dundersztyc.fitnessapp.elevation.domain;

import org.apache.lucene.util.SloppyMath;
import org.hibernate.validator.constraints.Range;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public record Coordinates(
        @Range(min = -90, max = 90) double latitude,
        @Range(min = -180, max = 180) double longitude) {

    public Coordinates(
            double latitude,
            double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        validate(this);
    }

    public Double getDistanceBetween(Coordinates next) {
        return SloppyMath.haversinMeters(this.latitude, this.longitude, next.latitude, next.longitude);
    }
}
