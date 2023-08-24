package pl.dundersztyc.fitnessapp.activity.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.activity.adapter.in.ActivityResponse;
import pl.dundersztyc.fitnessapp.activity.application.port.in.ActivityRecordRequest;
import pl.dundersztyc.fitnessapp.activity.application.port.in.ActivityRequest;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.fromJson;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;

class ActivityControllerTest extends AbstractIntegrationTest {

    private final static String ACTIVITY_URL = "/api/v1/activities/";

    @Test
    @WithMockUser
    void shouldAddActivity() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(1L, ActivityType.BASKETBALL, 85.5,
                List.of(new ActivityRecordRequest(1.0, 2.0, 3L, LocalDateTime.now()))
        );

        MvcResult addResult = addActivity(activityRequest)
                .andExpect(status().isOk())
                .andReturn();

        Activity.ActivityId activityId = getActivityIdFromResponse(addResult);

        assertThat(activityId).isNotNull();
    }


    @Test
    @WithMockUser
    void shouldAddAndGetActivity() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(1L, ActivityType.BASKETBALL, 85.5,
                List.of(new ActivityRecordRequest(1.0, 2.0, 3L, LocalDateTime.now()))
        );
        var activityId = getActivityIdFromResponse(addActivity(activityRequest).andReturn());

        var getResult = getActivity(activityId.value())
                .andExpect(status().isOk())
                .andReturn();

        var activityResponse = getActivityResponse(getResult);

        assertThat(activityResponse).isNotNull();
        assertThat(activityResponse.activityRecords()).hasSize(1);
    }

    @Test
    @WithMockUser
    void shouldAddActivitiesAndGetHistory() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime finishDate = LocalDateTime.of(2021, 1, 1, 1, 1);
        addActivities(
                new ActivityRequest(1L, ActivityType.BASKETBALL, 85.5,
                        List.of(new ActivityRecordRequest(1.0, 2.0, 3L, startDate))
                ),
                new ActivityRequest(1L, ActivityType.CYCLING, 85.5,
                        List.of(new ActivityRecordRequest(1.0, 2.0, 3L, finishDate))
                )
        );

        var getHistoryResult = getHistory(1L, startDate, finishDate)
                .andExpect(status().isOk())
                .andReturn();

        var activityResponseList = getActivityResponseAsList(getHistoryResult);

        assertThat(activityResponseList).isNotNull();
        assertThat(activityResponseList).hasSize(2);
    }

    @Test
    @WithMockUser
    void shouldAddActivitiesAndGetLast() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime finishDate = LocalDateTime.of(2021, 1, 1, 1, 1);
        addActivities(
                new ActivityRequest(1L, ActivityType.BASKETBALL, 85.5,
                        List.of(new ActivityRecordRequest(1.0, 2.0, 3L, startDate))
                ),
                new ActivityRequest(1L, ActivityType.CYCLING, 85.5,
                        List.of(new ActivityRecordRequest(1.0, 2.0, 3L, finishDate))
                )
        );

        var getLastResult = getLastActivities(1L, 1)
                .andExpect(status().isOk())
                .andReturn();

        var lastActivities = getActivityResponseAsList(getLastResult);

        assertThat(lastActivities).isNotNull();
        assertThat(lastActivities).hasSize(1);
        assertThat(lastActivities.get(0).activityType()).isEqualTo(ActivityType.CYCLING);
    }


    private void addActivities(ActivityRequest... activityRequests) throws Exception {
        for (var request : activityRequests) {
            addActivity(request);
        }
    }

    private ResultActions addActivity(ActivityRequest activityRequest) throws Exception {
        return mockMvc.perform(post(ACTIVITY_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(activityRequest)));
    }

    private ResultActions getActivity(Long activityId) throws Exception {
        return mockMvc.perform(get(ACTIVITY_URL + String.valueOf(activityId))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions getLastActivities(Long userId, int numberOfActivities) throws Exception {
        return mockMvc.perform(get(ACTIVITY_URL + "last")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", String.valueOf(userId))
                .param("numberOfActivities", String.valueOf(numberOfActivities)));
    }

    private ResultActions getHistory(Long userId, LocalDateTime startDate, LocalDateTime finishDate) throws Exception {
        return mockMvc.perform(get(ACTIVITY_URL + "history")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", String.valueOf(userId))
                .param("startDate", startDate.toString())
                .param("finishDate", finishDate.toString()));
    }

    private Activity.ActivityId getActivityIdFromResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(result.getResponse().getContentAsString(), Activity.ActivityId.class);
    }

    private ActivityResponse getActivityResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(result.getResponse().getContentAsString(), ActivityResponse.class);
    }

    private List<ActivityResponse> getActivityResponseAsList(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(
                result.getResponse().getContentAsString(),
                new TypeReference<List<ActivityResponse>>(){}
        );
    }

}