package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<WarehouseJpaEntity, Long> {

    Optional<WarehouseJpaEntity> findWarehouseBySellerUUIDAndRawMaterialData(UUID sellerUUID, RawMaterialData rawMaterialData);

    Optional<WarehouseJpaEntity> findWarehouseByWarehouseUUID(UUID warehouseUUID);

    Optional<List<WarehouseJpaEntity>> findWarehousesBySellerUUID(UUID sellerUUID);
}
