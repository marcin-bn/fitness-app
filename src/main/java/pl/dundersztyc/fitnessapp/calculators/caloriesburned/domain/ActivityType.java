package pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain;

import pl.dundersztyc.fitnessapp.common.weight.Weight;

public enum ActivityType {
    CYCLING(563),
    CALISTHENICS(246),
    WEIGHT_LIFTING(211),
    AEROBICS(457),
    RUNNING(633),
    BADMINTON(317),
    BASKETBALL(422),
    BOWLING(211),
    BOXING(633),
    FOOTBALL(563),
    GOLF(317),
    GYMNASTICS(281),
    HANDBALL(563),
    MARTIAL_ARTS(704),
    SOCCER(493),
    SQUASH(844),
    TABLE_TENNIS(281),
    TENNIS(493),
    VOLLEYBALL(385),
    WALKING(232),
    SWIMMING(493),
    CROSS_COUNTRY_SKIING(563),
    SNOW_SKIING(352);

    private final long caloriesBurnedPerHour; // for a 70kg person
    private final Weight averageWeight = Weight.fromKg(70);

    ActivityType(long caloriesBurnedPerHour) {
        this.caloriesBurnedPerHour = caloriesBurnedPerHour;
    }

    public long calculateCaloriesBurned(long minutes, Weight weight) {
        if (minutes < 0) {
            throw new IllegalArgumentException("minutes cannot be less than 0");
        }
        if (minutes == 0) return 0;
        return (long) (((double) minutes / 60) * (weight.getKg() / averageWeight.getKg()) * caloriesBurnedPerHour);
    }
}
