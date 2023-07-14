package pl.dundersztyc.fitnessapp.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByUsername(String username);
    Optional<UserJpaEntity> findByEmail(String email);
}
