package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "activityRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityRecordJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long activityId;
    private Long activitySequenceNumber;

    private Double latitude;
    private Double longitude;
    private Long heartRate;
    private LocalDateTime timestamp;
}
