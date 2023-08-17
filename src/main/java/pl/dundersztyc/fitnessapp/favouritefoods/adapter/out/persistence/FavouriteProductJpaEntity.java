package pl.dundersztyc.fitnessapp.favouritefoods.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "favouriteProduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class FavouriteProductJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String productId;
    private LocalDateTime timestamp;
}
