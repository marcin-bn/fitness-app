package pl.dundersztyc.fitnessapp.stepcounter.adapter.out.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import pl.dundersztyc.fitnessapp.AbstractTestcontainers;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepCounterProfile;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurement;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.StepMeasurementTestData.defaultStepMeasurement;

@DataJpaTest
@Import({StepCounterProfilePersistenceAdapter.class, StepCounterProfileMapper.class, StepMeasurementMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StepCounterProfilePersistenceAdapterTest extends AbstractTestcontainers {

    @Autowired
    private StepCounterProfilePersistenceAdapter persistenceAdapter;

    @Autowired
    private StepMeasurementRepository measurementRepository;

    @BeforeAll
    static void beforeAll(@Autowired StepMeasurementRepository measurementRepository) {
        measurementRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        measurementRepository.deleteAll();
    }

    @Test
    @Sql(scripts = "/LoadStepCounterProfile.sql")
    void shouldLoadStepCounterProfileSince() {
        LocalDateTime since = LocalDateTime.of(2018, 8, 10, 8, 0);
        var profile = persistenceAdapter.load(new User.UserId(1L), since);

        assertThat(profile.getMeasurementWindow().getMeasurements()).hasSize(5);
        assertThat(profile.getMinSteps()).isEqualTo(9500L);
        assertThat(profile.getMaxSteps()).isEqualTo(10250L);
    }

    @Test
    @Sql(scripts = "/LoadStepCounterProfile.sql")
    void shouldLoadStepCounterProfileFromTo() {
        LocalDateTime from = LocalDateTime.of(2018, 8, 12, 8, 0);
        LocalDateTime to = LocalDateTime.of(2018, 8, 14, 8, 0);
        var profile = persistenceAdapter.load(new User.UserId(2L), from, to);

        assertThat(profile.getMeasurementWindow().getMeasurements()).hasSize(3);
        assertThat(profile.getMinSteps()).isEqualTo(9500L);
        assertThat(profile.getMaxSteps()).isEqualTo(10000L);
    }

    @Test
    @Sql(scripts = "/LoadStepCounterProfile.sql")
    void shouldLoadStepCounterProfileSinceWithSpecifiedTypes() {
        LocalDateTime since = LocalDateTime.of(2018, 8, 8, 8, 0);
        var profile = persistenceAdapter.loadWithSpecifiedMeasurementTypes(new User.UserId(1L),
                List.of(StepMeasurementType.TRAINING), since);

        assertThat(profile.getMeasurementWindow().getMeasurements()).hasSize(4);
        assertThat(profile.getMinSteps()).isEqualTo(9750L);
        assertThat(profile.getMaxSteps()).isEqualTo(10500L);
    }

    @Test
    @Sql(scripts = "/LoadStepCounterProfile.sql")
    void shouldLoadStepCounterProfileFromToWithSpecifiedTypes() {
        LocalDateTime from = LocalDateTime.of(2018, 8, 8, 8, 0);
        LocalDateTime to = LocalDateTime.of(2018, 8, 14, 8, 0);
        var profile = persistenceAdapter.loadWithSpecifiedMeasurementTypes(new User.UserId(2L),
                List.of(StepMeasurementType.DAILY_ACTIVITY), from, to);

        assertThat(profile.getMeasurementWindow().getMeasurements()).hasSize(3);
        assertThat(profile.getMinSteps()).isEqualTo(9500L);
        assertThat(profile.getMaxSteps()).isEqualTo(11000L);
    }

    @Test
    void shouldUpdateMeasurements() {
        final User.UserId userId = new User.UserId(1L);
        final StepMeasurementWindow emptyMeasurementWindow = new StepMeasurementWindow();
        var profile = new StepCounterProfile(userId, emptyMeasurementWindow);

        profile.addMeasurement(defaultStepMeasurement()
                .userId(userId).steps(9000L).build());

        persistenceAdapter.updateMeasurements(profile);

        assertThat(measurementRepository.count()).isEqualTo(1);
        assertThat(measurementRepository.findAll().get(0).getSteps()).isEqualTo(9000L);
    }

    @Test
    void shouldNotUpdateMeasurementsWhenIdIsSet() {
        final User.UserId userId = new User.UserId(1L);
        final StepMeasurementWindow emptyMeasurementWindow = new StepMeasurementWindow();
        var profile = new StepCounterProfile(userId, emptyMeasurementWindow);

        profile.addMeasurement(defaultStepMeasurement()
                .id(new StepMeasurement.StepMeasurementId(1L))
                .userId(userId).steps(9000L).build());

        persistenceAdapter.updateMeasurements(profile);

        assertThat(measurementRepository.count()).isEqualTo(0);
    }


}