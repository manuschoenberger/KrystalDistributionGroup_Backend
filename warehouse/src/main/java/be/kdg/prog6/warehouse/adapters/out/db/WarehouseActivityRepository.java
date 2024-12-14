package be.kdg.prog6.warehouse.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WarehouseActivityRepository extends JpaRepository<WarehouseActivityJpaEntity, UUID> {

    List<WarehouseActivityJpaEntity> findByWarehouseUUID(UUID warehouseUUID);
}
