package pl.dundersztyc.fitnessapp.bodyweight.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

interface BodyWeightMeasurementRepository extends JpaRepository<BodyWeightMeasurementJpaEntity, Long> {

    @Query("""
            SELECT m FROM BodyWeightMeasurementJpaEntity m
            WHERE m.userId = :userId
            AND m.timestamp >= :since
            """)
    List<BodyWeightMeasurementJpaEntity> findByUserSince(@Param("userId") long userId,
                                                         @Param("since") LocalDateTime since);

    @Query("""
            SELECT m FROM BodyWeightMeasurementJpaEntity m
            WHERE m.userId = :userId
            AND m.timestamp >= :from
            AND m.timestamp <= :to
            """)
    List<BodyWeightMeasurementJpaEntity> findByUserFromTo(@Param("userId") long userId,
                                                          @Param("from") LocalDateTime from,
                                                          @Param("to") LocalDateTime to);
}
