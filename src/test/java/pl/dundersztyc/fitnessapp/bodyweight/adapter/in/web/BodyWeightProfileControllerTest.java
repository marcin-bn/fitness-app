package pl.dundersztyc.fitnessapp.bodyweight.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.dundersztyc.fitnessapp.AbstractIntegrationTest;
import pl.dundersztyc.fitnessapp.bodyweight.adapter.in.BodyWeightMeasurementResponse;
import pl.dundersztyc.fitnessapp.bodyweight.adapter.in.BodyWeightProgressResponse;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.in.BodyWeightMeasurementRequest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.fromJson;
import static pl.dundersztyc.fitnessapp.common.JsonMapper.toJsonString;

class BodyWeightProfileControllerTest extends AbstractIntegrationTest {

    private static final String MEASUREMENT_URL = "/api/v1/body-weight/measurements";

    @Test
    @WithMockUser
    void shouldAddMeasurement() throws Exception {

        BodyWeightMeasurementRequest measurementRequest = new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(65.50));

        MvcResult addResult = addMeasurement(measurementRequest)
                .andExpect(status().isOk())
                .andReturn();

        BodyWeightMeasurementResponse measurementResponse = getMeasurementResponse(addResult);

        assertThat(measurementResponse.userId()).isEqualTo(1L);
        assertThat(measurementResponse.weight().compareTo(BigDecimal.valueOf(65.50))).isEqualTo(0);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetProgress() throws Exception {

        addMeasurements(
                new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(65.50)),
                new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(63.20)),
                new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(60.50))
        );

        MvcResult progressResult = mockMvc
                .perform(get("/api/v1/body-weight/users/1/measurements/progress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", LocalDateTime.now().minusMinutes(10).toString()))
                .andExpect(status().isOk())
                .andReturn();

        BodyWeightProgressResponse progressResponse = fromJson(progressResult.getResponse().getContentAsString(), BodyWeightProgressResponse.class);

        assertThat(progressResponse.weightLoss().compareTo(BigDecimal.valueOf(5.00))).isEqualTo(0);
        assertThat(progressResponse.weeklyWeightLoss().compareTo(BigDecimal.ZERO)).isEqualTo(0);
    }

    @Test
    @WithMockUser
    void shouldAddMeasurementsAndGetMeasurementsHistory() throws Exception {

       addMeasurements(
                new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(65.50)),
                new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(63.20)),
                new BodyWeightMeasurementRequest(1L, BigDecimal.valueOf(60.50))
        );

        MvcResult historyResult = mockMvc
                .perform(get("/api/v1/body-weight/users/1/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", LocalDateTime.now().minusMinutes(10).toString()))
                .andExpect(status().isOk())
                .andReturn();

        List<BodyWeightMeasurementResponse> measurements = getMeasurementResponseAsList(historyResult);

        assertThat(measurements).hasSize(3);
    }

    private void addMeasurements(BodyWeightMeasurementRequest... measurementRequests) throws Exception {
        addMeasurements(List.of(measurementRequests));
    }

    private void addMeasurements(List<BodyWeightMeasurementRequest> measurementRequests) throws Exception {
        for (var measurement : measurementRequests) {
            addMeasurement(measurement);
        }
    }

    private ResultActions addMeasurement(BodyWeightMeasurementRequest measurementRequest) throws Exception {
        return mockMvc.perform(post(MEASUREMENT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(measurementRequest)));
    }

    private BodyWeightMeasurementResponse getMeasurementResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(result.getResponse().getContentAsString(), BodyWeightMeasurementResponse.class);
    }

    private List<BodyWeightMeasurementResponse> getMeasurementResponseAsList(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        return fromJson(
                result.getResponse().getContentAsString(),
                new TypeReference<List<BodyWeightMeasurementResponse>>(){}
        );
    }
}