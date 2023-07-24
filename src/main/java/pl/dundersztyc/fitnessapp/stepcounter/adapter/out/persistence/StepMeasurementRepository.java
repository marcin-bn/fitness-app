package pl.dundersztyc.fitnessapp.stepcounter.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dundersztyc.fitnessapp.stepcounter.domain.StepMeasurementType;

import java.time.LocalDateTime;
import java.util.List;

interface StepMeasurementRepository extends JpaRepository<StepMeasurementJpaEntity, Long> {

    @Query("""
            SELECT s FROM StepMeasurementJpaEntity s
            WHERE s.userId = :userId
            AND s.timestamp >= :since
            """)
    List<StepMeasurementJpaEntity> findByUserSince(@Param("userId") long userId,
                                                   @Param("since") LocalDateTime since);

    @Query("""
            SELECT s FROM FROM StepMeasurementJpaEntity s
            WHERE s.userId = :userId
            AND s.timestamp >= :from
            AND s.timestamp <= :to
            """)
    List<StepMeasurementJpaEntity> findByUserFromTo(@Param("userId") long userId,
                                                    @Param("from") LocalDateTime from,
                                                    @Param("to") LocalDateTime to);


    @Query("""
            SELECT s FROM StepMeasurementJpaEntity s
            WHERE s.userId = :userId
            AND s.timestamp >= :since
            AND s.type IN :types
            """)
    List<StepMeasurementJpaEntity> findByUserSinceWithTypes(@Param("userId") long userId,
                                                   @Param("since") LocalDateTime since,
                                                   @Param("types")List<StepMeasurementType> types);
    @Query("""
            SELECT s FROM FROM StepMeasurementJpaEntity s
            WHERE s.userId = :userId
            AND s.timestamp >= :from
            AND s.timestamp <= :to
            AND s.type IN :types
            """)
    List<StepMeasurementJpaEntity> findByUserFromToWithTypes(@Param("userId") long userId,
                                                   @Param("from") LocalDateTime from,
                                                   @Param("to") LocalDateTime to,
                                                   @Param("types")List<StepMeasurementType> types);
}
