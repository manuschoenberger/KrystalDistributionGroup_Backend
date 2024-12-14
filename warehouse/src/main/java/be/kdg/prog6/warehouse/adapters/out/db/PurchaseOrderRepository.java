package be.kdg.prog6.warehouse.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderJpaEntity, Long> {
    Optional<PurchaseOrderJpaEntity> findByReferenceUUID(UUID referenceUUID);
}
