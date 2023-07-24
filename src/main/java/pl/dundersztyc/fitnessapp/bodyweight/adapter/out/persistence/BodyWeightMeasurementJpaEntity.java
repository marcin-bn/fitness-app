package pl.dundersztyc.fitnessapp.bodyweight.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bodyWeightMeasurement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class BodyWeightMeasurementJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private BigDecimal weight;
    private LocalDateTime timestamp;

}
