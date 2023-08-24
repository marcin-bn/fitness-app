package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import pl.dundersztyc.fitnessapp.AbstractTestcontainers;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.activity.domain.ActivityRecord;
import pl.dundersztyc.fitnessapp.elevation.domain.Coordinates;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static pl.dundersztyc.fitnessapp.common.ActivityTestData.activityWithRecords;

@DataJpaTest
@Import({ActivityPersistenceAdapter.class, ActivityMapper.class, ActivityRecordMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActivityPersistenceAdapterTest extends AbstractTestcontainers {

    @Autowired
    private ActivityPersistenceAdapter persistenceAdapter;


    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityRecordRepository activityRecordRepository;

    @BeforeAll
    static void beforeAll(@Autowired ActivityRepository activityRepository,
                          @Autowired ActivityRecordRepository activityRecordRepository) {
        activityRepository.deleteAll();
        activityRecordRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        activityRepository.deleteAll();
        activityRecordRepository.deleteAll();
    }

    @Test
    @Sql(scripts = "/LoadActivity.sql")
    void shouldLoadActivityById() {
        var activity = persistenceAdapter.load(new Activity.ActivityId(1L));

        assertThat(activity).isNotNull();
        assertThat(activity.getActivityId().value()).isEqualTo(1);
        assertThat(activity.getActivityRecords()).hasSize(3);
        assertThat(activity.getActivityRecords().stream().map(ActivityRecord::heartRate).collect(Collectors.toList()))
                .isEqualTo(List.of(1L, 2L, 3L));
    }

    @Test
    @Sql(scripts = "/LoadActivity.sql")
    void shouldThrowWhenActivityIdDoesNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> persistenceAdapter.load(new Activity.ActivityId(3L)));
    }

    @Test
    @Sql(scripts = "/LoadActivity.sql")
    void shouldLoadActivitiesFromTo() {
        LocalDateTime from = LocalDateTime.of(2018, 8, 9, 1, 0);
        LocalDateTime to = LocalDateTime.of(2018, 8, 9, 15, 0);

        var activitiesFromTo = persistenceAdapter.loadFromTo(new User.UserId(1L), from, to);

        assertThat(activitiesFromTo).isNotNull();
        assertThat(activitiesFromTo).hasSize(1);
        assertThat(activitiesFromTo.get(0).getActivityRecords()).hasSize(1);
    }

    @Test
    @Sql(scripts = "/LoadActivity.sql")
    void shouldLoadLastActivities() {
        var lastActivities = persistenceAdapter.loadLast(new User.UserId(1L), 1);

        assertThat(lastActivities).isNotNull();
        assertThat(lastActivities).hasSize(1);
        assertThat(lastActivities.get(0).getActivityRecords()).hasSize(1);
    }

    @Test
    void shouldSaveActivity() {
        var activity = activityWithRecords(
                ActivityRecord.withoutId(new Coordinates(1, 2), 3L, LocalDateTime.of(2020, 1, 1, 1, 1)),
                ActivityRecord.withoutId(new Coordinates(4, 5), 6L, LocalDateTime.of(2021, 2, 2, 2, 2))
        );

        Activity.ActivityId activityId = persistenceAdapter.save(activity);

        assertThat(activityId).isNotNull();
        assertThat(activityRepository.count()).isEqualTo(1);
        assertThat(activityRecordRepository.count()).isEqualTo(2);
    }

    @Test
    void shouldNotSaveSameActivityTwice() {
        var activity = activityWithRecords(
                ActivityRecord.withoutId(new Coordinates(1, 2), 3L, LocalDateTime.of(2020, 1, 1, 1, 1)),
                ActivityRecord.withoutId(new Coordinates(4, 5), 6L, LocalDateTime.of(2021, 2, 2, 2, 2))
        );

        Activity.ActivityId activityId = persistenceAdapter.save(activity);

        var savedActivity = persistenceAdapter.load(activityId);

        persistenceAdapter.save(savedActivity);

        assertThat(activityRepository.count()).isEqualTo(1);
        assertThat(activityRecordRepository.count()).isEqualTo(2);
    }

}