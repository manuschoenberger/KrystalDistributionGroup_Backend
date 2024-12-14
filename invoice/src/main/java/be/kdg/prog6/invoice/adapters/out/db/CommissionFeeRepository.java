package be.kdg.prog6.invoice.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommissionFeeRepository extends JpaRepository<CommissionFeeJpaEntity, Long> {
}
