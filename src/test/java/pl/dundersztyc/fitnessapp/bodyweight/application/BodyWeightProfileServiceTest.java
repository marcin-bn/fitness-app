package pl.dundersztyc.fitnessapp.bodyweight.application;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.LoadBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.UpdateBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurementWindow;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static pl.dundersztyc.fitnessapp.common.BodyWeightMeasurementTestData.defaultBodyWeightMeasurement;

class BodyWeightProfileServiceTest {

    private final LoadBodyWeightProfilePort loadBodyWeightProfilePort = mock(LoadBodyWeightProfilePort.class);
    private final UpdateBodyWeightProfilePort updateBodyWeightProfilePort = mock(UpdateBodyWeightProfilePort.class);
    private final BodyWeightProfileService profileService = new BodyWeightProfileService(loadBodyWeightProfilePort, updateBodyWeightProfilePort);

    @Test
    void shouldAddMeasurement() {
        BodyWeightMeasurementRequest measurementRequest = new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(85.57));
        User.UserId userId = new User.UserId(1L);

        profileService.addMeasurement(measurementRequest);

        thenMeasurementHasBeenAdded(measurementRequest);
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

        givenProfileWithMeasurements(measurements);

        var progress = profileService.calculateProgress(1L, LocalDateTime.now(), Optional.empty());

        thenProfileHasBeenLoaded(userId);
        assertThat(progress.getWeightLoss().compareTo(BigDecimal.valueOf(5.25))).isEqualTo(0);
        assertThat(progress.getWeeklyWeightLoss().compareTo(BigDecimal.valueOf(5.25))).isEqualTo(0);
    }

    @Test
    void shouldLoadMeasurements() {
        User.UserId userId = new User.UserId(1L);

        List<BodyWeightMeasurement> measurements = List.of(
                defaultBodyWeightMeasurement().build(),
                defaultBodyWeightMeasurement().build()
        );

        givenProfileWithMeasurements(measurements);

        var loadedMeasurements = profileService.loadMeasurements(1L, LocalDateTime.now(), Optional.empty());

        thenProfileHasBeenLoaded(userId);

        assertThat(loadedMeasurements).hasSize(2);
    }

    private void thenProfileHasBeenLoaded(User.UserId userId) {
        ArgumentCaptor<User.UserId> userIdCaptor = ArgumentCaptor.forClass(User.UserId.class);
        then(loadBodyWeightProfilePort).should(times(1))
                .load(userIdCaptor.capture(), any(LocalDateTime.class));

        assertThat(userIdCaptor.getValue()).isEqualTo(userId);
    }

    private void thenMeasurementHasBeenAdded(BodyWeightMeasurementRequest measurementRequest) {
        ArgumentCaptor<BodyWeightProfile> profileCaptor = ArgumentCaptor.forClass(BodyWeightProfile.class);
        then(updateBodyWeightProfilePort).should(times(1))
                .updateMeasurements(profileCaptor.capture());

        BodyWeightProfile capturedProfile = profileCaptor.getValue();
        List<BodyWeightMeasurement> measurements = capturedProfile.getMeasurementWindow().getMeasurements();
        BodyWeightMeasurement measurement = measurements.get(0);

        assertThat(measurements).hasSize(1);
        assertThat(measurement.getUserId()).isEqualTo(new User.UserId(measurementRequest.userId()));
        assertThat(measurement.getWeight().compareTo(measurementRequest.weight())).isEqualTo(0);
    }

    private void givenProfileWithId(User.UserId userId) {
        given(loadBodyWeightProfilePort.load(eq(userId), any(LocalDateTime.class)))
                .willReturn(new BodyWeightProfile(userId, new BodyWeightMeasurementWindow()));
        given(loadBodyWeightProfilePort.load(eq(userId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(new BodyWeightProfile(userId, new BodyWeightMeasurementWindow()));
    }

    private void givenProfileWithMeasurements(List<BodyWeightMeasurement> measurements) {
        BodyWeightProfile profile = new BodyWeightProfile(new User.UserId(1L), new BodyWeightMeasurementWindow(measurements));

        given(loadBodyWeightProfilePort.load(any(User.UserId.class), any(LocalDateTime.class)))
                .willReturn(profile);
        given(loadBodyWeightProfilePort.load(any(User.UserId.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(profile);
    }

}