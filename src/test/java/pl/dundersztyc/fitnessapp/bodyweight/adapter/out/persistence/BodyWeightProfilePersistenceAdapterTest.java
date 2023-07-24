package pl.dundersztyc.fitnessapp.bodyweight.adapter.out.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import pl.dundersztyc.fitnessapp.AbstractTestcontainers;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurementWindow;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.BodyWeightMeasurementTestData.defaultBodyWeightMeasurement;

@DataJpaTest
@Import({BodyWeightProfilePersistenceAdapter.class, BodyWeightProfileMapper.class, BodyWeightMeasurementMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BodyWeightProfilePersistenceAdapterTest extends AbstractTestcontainers {


    @Autowired
    private BodyWeightProfilePersistenceAdapter persistenceAdapter;

    @Autowired
    private BodyWeightMeasurementRepository measurementRepository;

    @BeforeAll
    static void beforeAll(@Autowired BodyWeightMeasurementRepository measurementRepository) {
        measurementRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        measurementRepository.deleteAll();
    }

    @Test
    @Sql(scripts = "/LoadBodyWeightProfile.sql")
    void shouldLoadBodyWeightProfileSince() {
        LocalDateTime since = LocalDateTime.of(2018, 8, 10, 8, 0);
        var profile = persistenceAdapter.load(new User.UserId(1L), since);
        var measurementWindow = profile.getMeasurementWindow();

        assertThat(measurementWindow.getMeasurements()).hasSize(5);
        assertThat(profile.getMinWeight().compareTo(BigDecimal.valueOf(90L))).isEqualTo(0);
        assertThat(profile.getMaxWeight().compareTo(BigDecimal.valueOf(98L))).isEqualTo(0);
        assertThat(profile.isProgressMade()).isTrue();
    }

    @Test
    @Sql(scripts = "/LoadBodyWeightProfile.sql")
    void shouldLoadBodyWeightProfileFromTo() {
        LocalDateTime from = LocalDateTime.of(2018, 8, 12, 8, 0);
        LocalDateTime to = LocalDateTime.of(2018, 8, 14, 8, 0);
        var profile = persistenceAdapter.load(new User.UserId(2L), from, to);
        var measurementWindow = profile.getMeasurementWindow();

        assertThat(measurementWindow.getMeasurements()).hasSize(3);
        assertThat(profile.getMinWeight().compareTo(BigDecimal.valueOf(97L))).isEqualTo(0);
        assertThat(profile.getMaxWeight().compareTo(BigDecimal.valueOf(101L))).isEqualTo(0);
        assertThat(profile.isProgressMade()).isFalse();
    }

    @Test
    void shouldUpdateMeasurements() {
        final User.UserId userId = new User.UserId(1L);
        var emptyMeasurementWindow = new BodyWeightMeasurementWindow();
        var profile = new BodyWeightProfile(userId, emptyMeasurementWindow);
        profile.addMeasurement(defaultBodyWeightMeasurement()
                .userId(userId).weight(BigDecimal.valueOf(123L)).build());

        persistenceAdapter.updateMeasurements(profile);

        assertThat(measurementRepository.count()).isEqualTo(1);
        assertThat(measurementRepository.findAll().get(0).getWeight()).isEqualTo(BigDecimal.valueOf(123L));
    }

    @Test
    void shouldNotUpdateMeasurementsWhenIdIsSet() {
        final User.UserId userId = new User.UserId(1L);
        var emptyMeasurementWindow = new BodyWeightMeasurementWindow();
        var profile = new BodyWeightProfile(userId, emptyMeasurementWindow);
        profile.addMeasurement(defaultBodyWeightMeasurement()
                .id(new BodyWeightMeasurement.BodyWeightMeasurementId(1L))
                .userId(userId).weight(BigDecimal.valueOf(123L)).build());

        persistenceAdapter.updateMeasurements(profile);

        assertThat(measurementRepository.count()).isEqualTo(0);
    }
}
