package pl.dundersztyc.fitnessapp.bodyweight.application;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.LoadProfilePort;
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
import static pl.dundersztyc.fitnessapp.common.BodyWeightMeasurementTestData.defaultMeasurement;

class BodyWeightProfileServiceTest {

    private final LoadProfilePort loadProfilePort = mock(LoadProfilePort.class);
    private final UpdateBodyWeightProfilePort updateBodyWeightProfilePort = mock(UpdateBodyWeightProfilePort.class);
    private final BodyWeightProfileService profileService = new BodyWeightProfileService(loadProfilePort, updateBodyWeightProfilePort);

    @Test
    void shouldAddMeasurement() {
        BodyWeightMeasurementRequest measurementRequest = new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(85.57));
        User.UserId userId = new User.UserId(1L);

        givenProfileWithId(userId);

        profileService.addMeasurement(measurementRequest);

        thenProfileHasBeenLoaded(userId);
        thenMeasurementHasBeenAdded(measurementRequest);
    }

    @Test
    void shouldCalculateProgress() {
        User.UserId userId = new User.UserId(1L);

        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusWeeks(1);
        BigDecimal startWeight = BigDecimal.valueOf(90.50);
        BigDecimal endWeight = BigDecimal.valueOf(85.25);

        List<BodyWeightMeasurement> measurements = List.of(
                defaultMeasurement().timestamp(startDate).weight(startWeight).build(),
                defaultMeasurement().timestamp(endDate).weight(endWeight).build()
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
                defaultMeasurement().build(),
                defaultMeasurement().build()
        );

        givenProfileWithMeasurements(measurements);

        var loadedMeasurements = profileService.loadMeasurements(1L, LocalDateTime.now(), Optional.empty());

        thenProfileHasBeenLoaded(userId);

        assertThat(loadedMeasurements).hasSize(2);
    }

    private void thenProfileHasBeenLoaded(User.UserId userId) {
        ArgumentCaptor<User.UserId> userIdCaptor = ArgumentCaptor.forClass(User.UserId.class);
        then(loadProfilePort).should(times(1))
                .loadBodyWeightProfile(userIdCaptor.capture(), any(LocalDateTime.class));

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
        given(loadProfilePort.loadBodyWeightProfile(eq(userId), any(LocalDateTime.class)))
                .willReturn(new BodyWeightProfile(userId, new BodyWeightMeasurementWindow()));
        given(loadProfilePort.loadBodyWeightProfile(eq(userId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(new BodyWeightProfile(userId, new BodyWeightMeasurementWindow()));
    }

    private void givenProfileWithMeasurements(List<BodyWeightMeasurement> measurements) {
        BodyWeightProfile profile = new BodyWeightProfile(new User.UserId(1L), new BodyWeightMeasurementWindow(measurements));

        given(loadProfilePort.loadBodyWeightProfile(any(User.UserId.class), any(LocalDateTime.class)))
                .willReturn(profile);
        given(loadProfilePort.loadBodyWeightProfile(any(User.UserId.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(profile);
    }

}