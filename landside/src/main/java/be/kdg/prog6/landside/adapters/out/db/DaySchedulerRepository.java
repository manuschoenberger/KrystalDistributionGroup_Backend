package be.kdg.prog6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DaySchedulerRepository extends JpaRepository<DaySchedulerJpaEntity, Long> {

    Optional<DaySchedulerJpaEntity> findByDate(LocalDate date);
}
