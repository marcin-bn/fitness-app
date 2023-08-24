package pl.dundersztyc.fitnessapp.common.speed;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Duration;

import static pl.dundersztyc.fitnessapp.common.validation.Validation.validate;

public class Speed {

    @PositiveOrZero private final long meters;
    @NotNull private final Duration duration;

     public Speed(long meters, Duration duration) {
         this.meters = meters;
         this.duration = duration;
         validate(this);
     }

     public double getKilometersPerHour() {
         double kilometers = (double) meters / 1000;
         double hours = (double) duration.toMinutes() / 60;
         if (hours == 0.0) return 0.0;
         return kilometers / hours;
     }
}
