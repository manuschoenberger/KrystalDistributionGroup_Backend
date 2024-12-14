package be.kdg.prog6.waterside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShipmentOrderRepository extends JpaRepository<ShipmentOrderJpaEntity, UUID> {
    Optional<ShipmentOrderJpaEntity> findByReferenceUUID(UUID referenceUUID);
}
