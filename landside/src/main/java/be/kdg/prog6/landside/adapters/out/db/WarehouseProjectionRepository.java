package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WarehouseProjectionRepository extends JpaRepository<WarehouseProjectionJpaEntity, Long> {

    Optional<WarehouseProjectionJpaEntity> findByWarehouseUUID(UUID warehouseUUID);

    Optional<WarehouseProjectionJpaEntity> findBySellerUUIDAndRawMaterialData(UUID sellerUUID, RawMaterialData rawMaterialData);
}
