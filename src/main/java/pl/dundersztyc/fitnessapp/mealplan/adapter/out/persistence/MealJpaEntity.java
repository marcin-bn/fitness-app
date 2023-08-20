package pl.dundersztyc.fitnessapp.mealplan.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "meal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class MealJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private LocalDate day;
    private String productId;
    private Double weightInKg;

}
