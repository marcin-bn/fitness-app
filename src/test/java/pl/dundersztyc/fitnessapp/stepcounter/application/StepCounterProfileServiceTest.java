package pl.dundersztyc.fitnessapp.stepcounter.application;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.StepMeasurementRequest;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.out.LoadStepCounterProfilePort;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.out.UpdateStepCounterProfilePort;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepCounterProfile;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static pl.dundersztyc.fitnessapp.common.StepMeasurementTestData.defaultStepMeasurement;

class StepCounterProfileServiceTest {

    private final LoadStepCounterProfilePort loadStepCounterProfilePort = mock(LoadStepCounterProfilePort.class);
    private final UpdateStepCounterProfilePort updateStepCounterProfilePort = mock(UpdateStepCounterProfilePort.class);
    private final StepCounterProfileService profileService = new StepCounterProfileService(loadStepCounterProfilePort, updateStepCounterProfilePort);

    @Test
    void shouldAddMeasurement() {
        StepMeasurementRequest measurementRequest = new StepMeasurementRequest(1L, 8000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now());
        User.UserId userId = new User.UserId(1L);

        profileService.addMeasurement(measurementRequest);

        thenMeasurementHasBeenAdded(measurementRequest);
    }


    @Test
    void shouldCalculateAverageSteps() {
        User.UserId userId = new User.UserId(1L);

        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusWeeks(1);

        List<StepMeasurement> measurements = List.of(
                defaultStepMeasurement().timestamp(startDate).steps(5000L).build(),
                defaultStepMeasurement().timestamp(endDate).steps(10000L).build()
        );

        givenProfileWithMeasurements(measurements);

        long averageSteps = profileService.calculateAverageSteps(userId.value(), startDate, Optional.empty());

        thenProfileHasBeenLoaded(userId);
        assertThat(averageSteps).isEqualTo(7500L);
    }

    @Test
    void shouldCalculateAverageStepsWithSpecifiedTypes() {
        User.UserId userId = new User.UserId(1L);

        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusWeeks(1);

        List<StepMeasurement> measurements = List.of(
                defaultStepMeasurement().timestamp(startDate).steps(5000L).type(StepMeasurementType.DAILY_ACTIVITY).build(),
                defaultStepMeasurement().timestamp(endDate).steps(10000L).type(StepMeasurementType.DAILY_ACTIVITY).build()
        );
        List<StepMeasurementType> types = List.of(StepMeasurementType.DAILY_ACTIVITY);

        givenProfileWithMeasurementsAndTypes(measurements, types);

        long averageSteps = profileService.calculateAverageStepsWithSpecifiedTypes(userId.value(), types, startDate, Optional.empty());

        thenProfileHasBeenLoadedWithSpecifiedMeasurementTypes(types);
        assertThat(averageSteps).isEqualTo(7500L);
    }

    @Test
    void shouldCalculateAverageDailySteps() {
        User.UserId userId = new User.UserId(1L);

        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusDays(10);

        List<StepMeasurement> measurements = List.of(
                defaultStepMeasurement().timestamp(startDate).steps(5000L).build(),
                defaultStepMeasurement().timestamp(endDate).steps(10000L).build()
        );

        givenProfileWithMeasurements(measurements);

        long averageDailySteps = profileService.calculateAverageDailySteps(userId.value(), startDate, Optional.empty());

        thenProfileHasBeenLoaded(userId);
        assertThat(averageDailySteps).isEqualTo(1500L);
    }

    @Test
    void shouldCalculateAverageDailyStepsWithSpecifiedTypes() {
        User.UserId userId = new User.UserId(1L);

        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusDays(10);

        List<StepMeasurement> measurements = List.of(
                defaultStepMeasurement().timestamp(startDate).steps(5000L).type(StepMeasurementType.DAILY_ACTIVITY).build(),
                defaultStepMeasurement().timestamp(endDate).steps(10000L).type(StepMeasurementType.DAILY_ACTIVITY).build()
        );
        List<StepMeasurementType> types = List.of(StepMeasurementType.DAILY_ACTIVITY);

        givenProfileWithMeasurementsAndTypes(measurements, types);

        long averageDailySteps = profileService.calculateAverageDailyStepsWithSpecifiedTypes(userId.value(), types, startDate, Optional.empty());

        thenProfileHasBeenLoadedWithSpecifiedMeasurementTypes(types);
        assertThat(averageDailySteps).isEqualTo(1500L);
    }

    @Test
    void shouldLoadMeasurements() {
        User.UserId userId = new User.UserId(1L);

        List<StepMeasurement> measurements = List.of(
                defaultStepMeasurement().build(),
                defaultStepMeasurement().build()
        );

        givenProfileWithMeasurements(measurements);

        var loadedMeasurements = profileService.loadMeasurements(1L, LocalDateTime.now(), Optional.empty());

        thenProfileHasBeenLoaded(userId);

        assertThat(loadedMeasurements).hasSize(2);
    }

    @Test
    void shouldLoadMeasurementsWithSpecifiedTypes() {
        User.UserId userId = new User.UserId(1L);

        List<StepMeasurement> measurements = List.of(
                defaultStepMeasurement().type(StepMeasurementType.TRAINING).build(),
                defaultStepMeasurement().type(StepMeasurementType.TRAINING).build()
        );
        List<StepMeasurementType> types = List.of(StepMeasurementType.TRAINING);

        givenProfileWithMeasurementsAndTypes(measurements, types);

        var loadedMeasurements = profileService.loadMeasurementsWithSpecifiedTypes(1L, types, LocalDateTime.now(), Optional.empty());

        thenProfileHasBeenLoadedWithSpecifiedMeasurementTypes(types);

        assertThat(loadedMeasurements).hasSize(2);
    }

    private void thenProfileHasBeenLoaded(User.UserId userId) {
        ArgumentCaptor<User.UserId> userIdCaptor = ArgumentCaptor.forClass(User.UserId.class);
        then(loadStepCounterProfilePort).should(times(1))
                .load(userIdCaptor.capture(), any(LocalDateTime.class));

        assertThat(userIdCaptor.getValue()).isEqualTo(userId);
    }

    private void thenProfileHasBeenLoadedWithSpecifiedMeasurementTypes(List<StepMeasurementType> types) {
        ArgumentCaptor<List<StepMeasurementType>> typesCaptor = ArgumentCaptor.forClass(List.class);
        then(loadStepCounterProfilePort).should(times(1))
                .loadWithSpecifiedMeasurementTypes(any(User.UserId.class), typesCaptor.capture(), any(LocalDateTime.class));

        assertThat(typesCaptor.getValue()).isEqualTo(types);
    }

    private void thenMeasurementHasBeenAdded(StepMeasurementRequest measurementRequest) {
        ArgumentCaptor<StepCounterProfile> profileCaptor = ArgumentCaptor.forClass(StepCounterProfile.class);
        then(updateStepCounterProfilePort).should(times(1))
                .updateMeasurements(profileCaptor.capture());

        StepCounterProfile capturedProfile = profileCaptor.getValue();
        List<StepMeasurement> measurements = capturedProfile.getMeasurementWindow().getMeasurements();
        StepMeasurement measurement = measurements.get(0);

        assertThat(measurements).hasSize(1);
        assertThat(measurement.getUserId()).isEqualTo(new User.UserId(measurementRequest.userId()));
        assertThat(measurement.getSteps()).isEqualTo(measurementRequest.steps());
        assertThat(measurement.getType()).isEqualTo(measurementRequest.type());
    }

    private void givenProfileWithId(User.UserId userId) {
        given(loadStepCounterProfilePort.load(eq(userId), any(LocalDateTime.class)))
                .willReturn(new StepCounterProfile(userId, new StepMeasurementWindow()));
        given(loadStepCounterProfilePort.load(eq(userId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(new StepCounterProfile(userId, new StepMeasurementWindow()));
    }

    private void givenProfileWithIdAndTypes(User.UserId userId, List<StepMeasurementType> types) {
        given(loadStepCounterProfilePort.loadWithSpecifiedMeasurementTypes(eq(userId), eq(types), any(LocalDateTime.class)))
                .willReturn(new StepCounterProfile(userId, new StepMeasurementWindow()));
        given(loadStepCounterProfilePort.loadWithSpecifiedMeasurementTypes(eq(userId), eq(types), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(new StepCounterProfile(userId, new StepMeasurementWindow()));
    }

    private void givenProfileWithMeasurements(List<StepMeasurement> measurements) {
        StepCounterProfile profile = new StepCounterProfile(new User.UserId(1L), new StepMeasurementWindow(measurements));

        given(loadStepCounterProfilePort.load(any(User.UserId.class), any(LocalDateTime.class)))
                .willReturn(profile);
        given(loadStepCounterProfilePort.load(any(User.UserId.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(profile);
    }

    private void givenProfileWithMeasurementsAndTypes(List<StepMeasurement> measurements, List<StepMeasurementType> types) {
        assertThat(types).hasSizeGreaterThan(0);

        // set correct type
        List<StepMeasurement> measurementSpies = new ArrayList<>();
        for (StepMeasurement measurement : measurements) {
            StepMeasurement measurementSpy = spy(measurement);
            given(measurementSpy.getType())
                    .willReturn(types.get(0));
            measurementSpies.add(measurementSpy);
        }

        StepCounterProfile profile = new StepCounterProfile(new User.UserId(1L), new StepMeasurementWindow(measurementSpies));

        given(loadStepCounterProfilePort.loadWithSpecifiedMeasurementTypes(any(User.UserId.class), eq(types), any(LocalDateTime.class)))
                .willReturn(profile);
        given(loadStepCounterProfilePort.loadWithSpecifiedMeasurementTypes(any(User.UserId.class), eq(types), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(profile);
    }


}