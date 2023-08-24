package pl.dundersztyc.fitnessapp.activity.domain;

import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;
import pl.dundersztyc.fitnessapp.elevation.externalapi.elevationapi.ElevationService;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static pl.dundersztyc.fitnessapp.common.ActivityTestData.activityWithRecords;

class ActivityTest {

    @Test
    void shouldGetStartDate() {
        var startTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        var finishTime = startTime.plusHours(1);

        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, startTime),
                ActivityRecord.withoutId(null, null, finishTime)
        );

        var startDate = activity.getStartDate();

        assertThat(startDate).isEqualTo(startTime);
    }

    @Test
    void shouldCalculateDuration() {
        var startTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        var finishTime = startTime.plusHours(1);

        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, startTime),
                ActivityRecord.withoutId(null, null, finishTime)
        );

        var duration = activity.calculateDuration();

        assertThat(duration.toMinutes()).isEqualTo(60);
    }

    @Test
    void shouldReturnDurationZeroWhenCalculateDurationWithTooFewRecords() {
        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, LocalDateTime.now())
        );

        var duration = activity.calculateDuration();

        assertThat(duration).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldCalculateCaloriesBurned() {
        var startTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        var finishTime = startTime.plusHours(1);

        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, startTime),
                ActivityRecord.withoutId(null, null, finishTime)
        );

        var caloriesBurned = activity.calculateCaloriesBurned();

        assertThat(caloriesBurned).isEqualTo(723);
    }

    @Test
    void shouldCalculateCaloriesBurnedWhenDurationIsZero() {
        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, LocalDateTime.now())
        );

        var caloriesBurned = activity.calculateCaloriesBurned();

        assertThat(caloriesBurned).isEqualTo(0);
    }

    @Test
    void shouldCalculateAverageHeartRate() {
        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, 100L, LocalDateTime.now()),
                ActivityRecord.withoutId(null, null, LocalDateTime.now()),
                ActivityRecord.withoutId(null, 150L, LocalDateTime.now()),
                ActivityRecord.withoutId(null, null, LocalDateTime.now()),
                ActivityRecord.withoutId(null, 200L, LocalDateTime.now())
        );

        var averageHeartRate = activity.calculateAverageHeartRate();

        assertThat(averageHeartRate).isEqualTo(150);
    }

    @Test
    void shouldThrowWhenCalculateAverageHeartRateAndNoDataIsProvided() {
        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, LocalDateTime.now())
        );

        assertThrows(IllegalStateException.class,
                activity::calculateAverageHeartRate);

    }

    @Test
    void shouldCalculateAverageAltitude() {
        var getAltitudeUseCase = new ElevationService();
        var c1 = new Coordinates(10, 20); // altitude: 402
        var c2 = new Coordinates(20, 30); // altitude: 296
        var c3 = new Coordinates(30, 41); // altitude: 610

        var activity = activityWithRecords(
                ActivityRecord.withoutId(c1, null, LocalDateTime.now()),
                ActivityRecord.withoutId(c2, null, LocalDateTime.now()),
                ActivityRecord.withoutId(c3, null, LocalDateTime.now())
        );

        var averageAltitude = activity.calculateAverageAltitude(getAltitudeUseCase);

        assertThat(averageAltitude).isEqualTo(436);
    }

    @Test
    void shouldThrowWhenCalculateAverageAltitudeAndCoordinatesAreNotProvided() {
        var getAltitudeUseCase = new ElevationService();
        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, LocalDateTime.now())
        );

        assertThrows(IllegalStateException.class,
                () -> activity.calculateAverageAltitude(getAltitudeUseCase));
    }

    @Test
    void shouldCalculateDistanceInMeters() {

        // distance between c1 and c2 is 1 meter
        var c1 = new Coordinates(1, 1);
        var c2 = new Coordinates(1, 1.00001);


        var activity = activityWithRecords(
                ActivityRecord.withoutId(c1, null, LocalDateTime.now()),
                ActivityRecord.withoutId(c2, null, LocalDateTime.now()),
                ActivityRecord.withoutId(null, null, LocalDateTime.now()),

                ActivityRecord.withoutId(c1, null, LocalDateTime.now()),
                ActivityRecord.withoutId(c2, null, LocalDateTime.now()),
                ActivityRecord.withoutId(null, null, LocalDateTime.now())
        );

        var distanceInMeters = activity.calculateDistanceInMeters();

        assertThat(distanceInMeters).isEqualTo(3);
    }

    @Test
    void shouldCalculateDistanceInMetersWhenCoordinatesAreNotProvided() {

        var activity = activityWithRecords(
                ActivityRecord.withoutId(null, null, LocalDateTime.now()),
                ActivityRecord.withoutId(null, null, LocalDateTime.now())
        );

        var distanceInMeters = activity.calculateDistanceInMeters();

        assertThat(distanceInMeters).isEqualTo(0);
    }

    @Test
    void shouldGetAverageSpeed() {
        // distance between c1 and c2 is 1111 meters
        var c1 = new Coordinates(1, 1);
        var c2 = new Coordinates(1, 1.01);

        var startTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        var finishTime = startTime.plusHours(1);

        var activity = activityWithRecords(
                ActivityRecord.withoutId(c1, null, startTime),
                ActivityRecord.withoutId(c2, null, finishTime)
        );

        var averageSpeed = activity.getAverageSpeed();

        assertThat(averageSpeed.getKilometersPerHour()).isEqualTo(1.111);
    }

    @Test
    void shouldGetAverageSpeedWhenDurationIsZero() {
        var time = LocalDateTime.of(2020, 1, 1, 1, 1);

        var activity = activityWithRecords(
                ActivityRecord.withoutId(new Coordinates(1, 1), null, time),
                ActivityRecord.withoutId(new Coordinates(1, 1.01), null, time)
        );

        var averageSpeed = activity.getAverageSpeed();

        assertThat(averageSpeed.getKilometersPerHour()).isEqualTo(0);
    }
}