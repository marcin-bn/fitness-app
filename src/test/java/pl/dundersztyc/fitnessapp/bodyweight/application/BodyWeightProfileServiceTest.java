package pl.dundersztyc.fitnessapp.bodyweight.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.LoadBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.UpdateBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.BodyWeightMeasurementTestData.defaultBodyWeightMeasurement;

class BodyWeightProfileServiceTest {

    private final InMemoryBodyWeightMeasurementRepository repo = new InMemoryBodyWeightMeasurementRepository();
    private final LoadBodyWeightProfilePort loadBodyWeightProfilePort = repo;
    private final UpdateBodyWeightProfilePort updateBodyWeightProfilePort = repo;

    private final BodyWeightProfileService profileService = new BodyWeightProfileService(loadBodyWeightProfilePort, updateBodyWeightProfilePort);

    @BeforeEach
    void clearDatabase() {
        repo.measurements.clear();
    }

    @Test
    void shouldAddMeasurement() {
        BodyWeightMeasurementRequest measurementRequest = new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(85.57));

        profileService.addMeasurement(measurementRequest);

        assertThat(repo.measurements.get(new User.UserId(1L)))
                .hasSize(1);
    }

    @Test
    void shouldCalculateProgress() {
        User.UserId userId = new User.UserId(1L);

        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusWeeks(1);

        List<BodyWeightMeasurement> measurements = List.of(
                defaultBodyWeightMeasurement().timestamp(startDate).weight(BigDecimal.valueOf(90.50)).build(),
                defaultBodyWeightMeasurement().timestamp(endDate).weight(BigDecimal.valueOf(85.25)).build()
        );
        addMeasurements(userId, measurements);

        var progress = profileService.calculateProgress(1L, startDate, Optional.of(endDate));

        assertThat(progress.getWeightLoss().compareTo(BigDecimal.valueOf(5.25))).isEqualTo(0);
        assertThat(progress.getWeeklyWeightLoss().compareTo(BigDecimal.valueOf(5.25))).isEqualTo(0);
    }


    @Test
    void shouldLoadMeasurementsFromTo() {
        User.UserId userId = new User.UserId(1L);
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusWeeks(1);

        List<BodyWeightMeasurement> measurements = List.of(
                defaultBodyWeightMeasurement().timestamp(startDate).build(),
                defaultBodyWeightMeasurement().timestamp(endDate).build()
        );
        addMeasurements(userId, measurements);

        var loadedMeasurements = profileService.loadMeasurements(1L, startDate, Optional.of(endDate));

        assertThat(loadedMeasurements).hasSize(2);
    }

    @Test
    void shouldLoadMeasurementsFrom() {
        User.UserId userId = new User.UserId(1L);
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);

        List<BodyWeightMeasurement> measurements = List.of(
                defaultBodyWeightMeasurement().timestamp(startDate).build(),
                defaultBodyWeightMeasurement().timestamp(startDate.plusWeeks(1)).build()
        );
        addMeasurements(userId, measurements);

        var loadedMeasurements = profileService.loadMeasurements(1L, startDate, Optional.empty());

        assertThat(loadedMeasurements).hasSize(2);
    }

    private void addMeasurements(User.UserId userId, List<BodyWeightMeasurement> measurements) {
        repo.measurements.put(userId, measurements);
    }
}