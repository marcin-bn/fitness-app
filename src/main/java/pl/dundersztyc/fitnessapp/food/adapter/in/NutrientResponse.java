package pl.dundersztyc.fitnessapp.food.adapter.in;

import lombok.NonNull;
import pl.dundersztyc.fitnessapp.food.domain.Nutrient;

record NutrientResponse(
        double quantity,
        @NonNull String unit
) {

    public static NutrientResponse of(Nutrient nutrient, @NonNull Unit unit) {
        return (nutrient == null) ? null
                : new NutrientResponse(unit.getQuantity(nutrient), unit.symbol);
    }

    public enum Unit {
        Gram("g") {
            @Override
            public double getQuantity(Nutrient nutrient) {
                return nutrient.getGrams();
            }
        },
        Milligram("mg") {
            @Override
            public double getQuantity(Nutrient nutrient) {
                return nutrient.getMilligrams();
            }
        },
        Microgram("µg") {
            @Override
            public double getQuantity(Nutrient nutrient) {
                return nutrient.getMicrograms();
            }
        };

        final String symbol;

        Unit(String symbol) {
            this.symbol = symbol;
        }

        public abstract double getQuantity(Nutrient nutrient);
    }

}
