package be.kdg.prog6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WeighingBridgeTicketRepository extends JpaRepository<WeighingBridgeTicketJpaEntity, Long> {
    Optional<WeighingBridgeTicketJpaEntity> findByLicensePlate(String licensePlate);
}
