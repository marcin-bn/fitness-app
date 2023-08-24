package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

interface ActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {
    @Query("""
            SELECT a FROM ActivityJpaEntity a
            WHERE a.userId = :userId
            AND a.startDate >= :from
            AND a.startDate <= :to
            """)
    List<ActivityJpaEntity> findByUserFromTo(@Param("userId") long userId,
                                             @Param("from") LocalDateTime from,
                                             @Param("to") LocalDateTime to);

    @Query("""
            SELECT a FROM ActivityJpaEntity a
            WHERE a.userId = :userId
            ORDER BY a.startDate DESC
            LIMIT :limit
            """)
    List<ActivityJpaEntity> findLastByUser(@Param("userId") long userId,
                                           @Param("limit") int numberOfActivities);
}
