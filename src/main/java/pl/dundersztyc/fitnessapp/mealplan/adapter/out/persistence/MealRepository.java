package pl.dundersztyc.fitnessapp.mealplan.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

interface MealRepository extends JpaRepository<MealJpaEntity, Long> {

    @Query("""
            SELECT m FROM MealJpaEntity m
            WHERE m.userId = :userId
            AND m.day >= :from
            AND m.day <= :to
            """)
    List<MealJpaEntity> findByUserFromTo(@Param("userId") long userId,
                                         @Param("from") LocalDate from,
                                         @Param("to") LocalDate to);
}
