package pl.dundersztyc.fitnessapp.activity.application;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.dundersztyc.fitnessapp.activity.application.port.in.ActivityRecordRequest;
import pl.dundersztyc.fitnessapp.activity.application.port.in.ActivityRequest;
import pl.dundersztyc.fitnessapp.activity.application.port.out.LoadActivitiesPort;
import pl.dundersztyc.fitnessapp.activity.application.port.out.SaveActivityPort;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class ActivityServiceTest {

    private final LoadActivitiesPort loadActivitiesPort = mock(LoadActivitiesPort.class);
    private final SaveActivityPort saveActivityPort = mock(SaveActivityPort.class);

    private final ActivityService activityService = new ActivityService(loadActivitiesPort, saveActivityPort);

    @Test
    void shouldAddActivity() {
        var startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        var activityRequest = new ActivityRequest(1L, ActivityType.BASKETBALL, 85.5, List.of(
                new ActivityRecordRequest(1.0, 2.0, 3L, startDate),
                new ActivityRecordRequest(null, 5.0, 6L, startDate.plusMinutes(1)))
        );

        activityService.addActivity(activityRequest);

        ArgumentCaptor<Activity> activityCaptor = ArgumentCaptor.forClass(Activity.class);
        then(saveActivityPort).should(times(1))
                .save(activityCaptor.capture());

        var activity = activityCaptor.getValue();

        assertThat(activity).isNotNull();
        assertThat(activity.getActivityRecords()).hasSize(2);
        assertThat(activity.getActivityRecords().get(1).coordinates()).isNull();
    }

}