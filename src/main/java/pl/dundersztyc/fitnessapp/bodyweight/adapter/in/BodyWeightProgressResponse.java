package pl.dundersztyc.fitnessapp.bodyweight.adapter.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProgress;

import java.math.BigDecimal;

public record BodyWeightProgressResponse(
        @NonNull BigDecimal weightLoss,
        @NonNull BigDecimal weeklyWeightLoss) {

    public static BodyWeightProgressResponse of(BodyWeightProgress progress) {
        return new BodyWeightProgressResponse(
                progress.getWeightLoss(),
                progress.getWeeklyWeightLoss());
    }
}
