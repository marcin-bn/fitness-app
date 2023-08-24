package pl.dundersztyc.fitnessapp.activity.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRecordRepository extends JpaRepository<ActivityRecordJpaEntity, Long> {
    Optional<List<ActivityRecordJpaEntity>> findAllByActivityId(Long activityId);
}
