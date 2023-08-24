package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ActivityJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    private Double weightInKg;

    private LocalDateTime startDate;
}
