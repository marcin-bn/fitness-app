package pl.dundersztyc.fitnessapp.stepcounter.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;

@Entity
@Table(name = "stepMeasurement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StepMeasurementJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long steps;

    @Enumerated(EnumType.STRING)
    private StepMeasurementType type;

    private LocalDateTime timestamp;
}
