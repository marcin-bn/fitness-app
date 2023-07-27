package pl.dundersztyc.fitnessapp.stepcounter.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.stepcounter.adapter.in.StepMeasurementResponse;
import pl.dundersztyc.fitnessapp.stepcounter.application.port.in.StepMeasurementRequest;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.fromJson;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;

class StepCounterProfileControllerTest extends AbstractIntegrationTest {

    private static final String MEASUREMENT_URL = "/api/v1/step-counter/measurements";


    @Test
    @WithMockUser
    void shouldAddMeasurement() throws Exception {
        StepMeasurementRequest measurementRequest = new StepMeasurementRequest(1L, 10000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now());

        MvcResult addResult = addMeasurement(measurementRequest)
                .andExpect(status().isOk())
                .andReturn();

        StepMeasurementResponse measurementResponse = getMeasurementResponse(addResult);

        assertThat(measurementResponse.userId()).isEqualTo(1L);
        assertThat(measurementResponse.steps()).isEqualTo(10000L);
        assertThat(measurementResponse.type()).isEqualTo(StepMeasurementType.DAILY_ACTIVITY);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetMeasurementsHistory() throws Exception {

        addMeasurements(createMeasurementRequests(3));

        MvcResult historyResult = mockMvc
                .perform(get("/api/v1/step-counter/users/1/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", LocalDateTime.now().minusMinutes(10).toString()))
                .andExpect(status().isOk())
                .andReturn();

        List<StepMeasurementResponse> measurements = getMeasurementResponseAsList(historyResult);

        assertThat(measurements).hasSize(3);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetMeasurementsHistoryWithSpecifiedTypes() throws Exception {
        addMeasurements(
                new StepMeasurementRequest(1L, 9000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now()),
                new StepMeasurementRequest(1L, 2000L, StepMeasurementType.TRAINING, LocalDateTime.now()),
                new StepMeasurementRequest(1L, 10000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now())
        );

        MvcResult historyResult = mockMvc
                .perform(get("/api/v1/step-counter/users/1/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", LocalDateTime.now().minusMinutes(10).toString())
                        .param("types", new String[]{StepMeasurementType.DAILY_ACTIVITY.name()}))
                .andExpect(status().isOk())
                .andReturn();

        List<StepMeasurementResponse> measurements = getMeasurementResponseAsList(historyResult);

        assertThat(measurements).hasSize(2);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetAverageSteps() throws Exception {

        addMeasurements(
                new StepMeasurementRequest(1L, 9000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now()),
                new StepMeasurementRequest(1L, 8000L, StepMeasurementType.TRAINING, LocalDateTime.now()),
                new StepMeasurementRequest(1L, 10000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now())
        );

        MvcResult avgStepsResult = mockMvc
                .perform(get("/api/v1/step-counter/users/1/measurements/steps/average")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", LocalDateTime.now().minusMinutes(10).toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long averageSteps = Long.valueOf(avgStepsResult.getResponse().getContentAsString());

        assertThat(averageSteps).isEqualTo(9000L);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetAverageStepsWithSpecifiedTypes() throws Exception {

        addMeasurements(
                new StepMeasurementRequest(1L, 9000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now()),
                new StepMeasurementRequest(1L, 8000L, StepMeasurementType.TRAINING, LocalDateTime.now()),
                new StepMeasurementRequest(1L, 10000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now())
        );

        MvcResult avgStepsResult = mockMvc
                .perform(get("/api/v1/step-counter/users/1/measurements/steps/average")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", LocalDateTime.now().minusMinutes(10).toString())
                        .param("types", new String[]{StepMeasurementType.DAILY_ACTIVITY.name()}))
                .andExpect(status().isOk())
                .andReturn();

        Long averageSteps = Long.valueOf(avgStepsResult.getResponse().getContentAsString());

        assertThat(averageSteps).isEqualTo(9500L);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetAverageDailySteps() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime inBetweenDate = LocalDateTime.of(2020, 1, 5, 1, 1);
        LocalDateTime finishDate = LocalDateTime.of(2020, 1, 11, 1, 1);

        addMeasurements(
                new StepMeasurementRequest(1L, 9000L, StepMeasurementType.DAILY_ACTIVITY, startDate),
                new StepMeasurementRequest(1L, 8000L, StepMeasurementType.TRAINING, inBetweenDate),
                new StepMeasurementRequest(1L, 10000L, StepMeasurementType.DAILY_ACTIVITY, finishDate)
        );


        MvcResult avgDailyStepsResult = mockMvc
                .perform(get("/api/v1/step-counter/users/1/measurements/steps/average-daily")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", startDate.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long averageSteps = Long.valueOf(avgDailyStepsResult.getResponse().getContentAsString());

        assertThat(averageSteps).isEqualTo(2700L);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetAverageDailyStepsWithSpecifiedTypes() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime inBetweenDate = LocalDateTime.of(2020, 1, 5, 1, 1);
        LocalDateTime finishDate = LocalDateTime.of(2020, 1, 11, 1, 1);

        addMeasurements(
                new StepMeasurementRequest(1L, 9000L, StepMeasurementType.DAILY_ACTIVITY, startDate),
                new StepMeasurementRequest(1L, 8000L, StepMeasurementType.TRAINING, inBetweenDate),
                new StepMeasurementRequest(1L, 10000L, StepMeasurementType.DAILY_ACTIVITY, finishDate)
        );


        MvcResult avgDailyStepsResult = mockMvc
                .perform(get("/api/v1/step-counter/users/1/measurements/steps/average-daily")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", startDate.toString())
                        .param("types", new String[]{StepMeasurementType.DAILY_ACTIVITY.name()}))
                .andExpect(status().isOk())
                .andReturn();

        Long averageSteps = Long.valueOf(avgDailyStepsResult.getResponse().getContentAsString());

        assertThat(averageSteps).isEqualTo(1900L);
    }

    private StepMeasurementResponse getMeasurementResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(result.getResponse().getContentAsString(), StepMeasurementResponse.class);
    }

    private List<StepMeasurementResponse> getMeasurementResponseAsList(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(
                result.getResponse().getContentAsString(),
                new TypeReference<List<StepMeasurementResponse>>(){}
        );
    }


    private void addMeasurements(StepMeasurementRequest... measurementRequests) throws Exception {
        addMeasurements(List.of(measurementRequests));
    }

    private void addMeasurements(List<StepMeasurementRequest> measurementRequests) throws Exception {
        for (var measurement : measurementRequests) {
            addMeasurement(measurement);
        }
    }

    private ResultActions addMeasurement(StepMeasurementRequest measurementRequest) throws Exception {
        return mockMvc.perform(post(MEASUREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(measurementRequest)));
    }


    private List<StepMeasurementRequest> createMeasurementRequests(int numberOfRequests) {
        return IntStream.range(0, numberOfRequests)
                .mapToObj(i -> defaultMeasurementRequest())
                .collect(Collectors.toList());
    }

    private StepMeasurementRequest defaultMeasurementRequest() {
        return new StepMeasurementRequest(1L, 9000L, StepMeasurementType.DAILY_ACTIVITY, LocalDateTime.now());
    }
}