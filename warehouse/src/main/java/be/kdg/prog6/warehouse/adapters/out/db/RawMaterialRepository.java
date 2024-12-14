package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RawMaterialRepository extends JpaRepository<RawMaterialJpaEntity, UUID> {
    Optional<RawMaterialJpaEntity> findByRawMaterialData(RawMaterialData rawMaterialData);
}
