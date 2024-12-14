package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.landside.domain.Warehouse;
import be.kdg.prog6.landside.ports.out.WarehouseLoadPort;
import be.kdg.prog6.landside.ports.out.WarehouseUpdatePort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WarehouseProjectionDBAdapter implements WarehouseLoadPort, WarehouseUpdatePort {

    private final WarehouseProjectionRepository warehouseProjectionRepository;

    public WarehouseProjectionDBAdapter(WarehouseProjectionRepository warehouseProjectionRepository) {
        this.warehouseProjectionRepository = warehouseProjectionRepository;
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        WarehouseProjectionJpaEntity warehouseProjectionJpaEntity = warehouseProjectionRepository
                .findByWarehouseUUID(warehouse.getWarehouseUUID().uuid())
                .orElseGet(() -> new WarehouseProjectionJpaEntity(warehouse.getWarehouseUUID().uuid()));

        warehouseProjectionJpaEntity.setSellerUUID(warehouse.getSellerUUID().uuid());
        warehouseProjectionJpaEntity.setWarehouseNumber(warehouse.getWarehouseNumber());
        warehouseProjectionJpaEntity.setRawMaterialData(warehouse.getRawMaterialData());
        warehouseProjectionJpaEntity.setCurrentCapacity(warehouse.getCurrentCapacity());

        warehouseProjectionRepository.save(warehouseProjectionJpaEntity);
    }

    @Override
    public Optional<Warehouse> loadWarehouseByWarehouseUUID(WarehouseUUID warehouseUUID, SellerUUID sellerUUID, int warehouseNumber, RawMaterialData rawMaterial) {
        Optional<WarehouseProjectionJpaEntity> warehouseProjectionJpaEntityOptional = warehouseProjectionRepository.findByWarehouseUUID(warehouseUUID.uuid());

        if (warehouseProjectionJpaEntityOptional.isPresent()) {
            Warehouse warehouse = new Warehouse(
                    new WarehouseUUID(warehouseProjectionJpaEntityOptional.get().getWarehouseUUID()),
                    new SellerUUID(warehouseProjectionJpaEntityOptional.get().getSellerUUID()),
                    warehouseProjectionJpaEntityOptional.get().getWarehouseNumber(),
                    warehouseProjectionJpaEntityOptional.get().getRawMaterialData(),
                    warehouseProjectionJpaEntityOptional.get().getCurrentCapacity()
            );

            return Optional.of(warehouse);
        }

        return Optional.of(new Warehouse(warehouseUUID, sellerUUID, warehouseNumber, rawMaterial, 0));
    }

    @Override
    public Optional<Warehouse> loadWarehouseBySellerIdAndPayloadData(SellerUUID sellerUUID, RawMaterialData rawMaterialData) {
        Optional<WarehouseProjectionJpaEntity> warehouseJpaEntity = warehouseProjectionRepository.findBySellerUUIDAndRawMaterialData(sellerUUID.uuid(), rawMaterialData);

        if (warehouseJpaEntity.isPresent()) {
            Warehouse warehouse = new Warehouse(
                    new WarehouseUUID(warehouseJpaEntity.get().getWarehouseUUID()),
                    new SellerUUID(warehouseJpaEntity.get().getSellerUUID()),
                    warehouseJpaEntity.get().getWarehouseNumber(),
                    warehouseJpaEntity.get().getRawMaterialData(),
                    warehouseJpaEntity.get().getCurrentCapacity()
            );

            return Optional.of(warehouse);
        }

        return Optional.of(new Warehouse(new WarehouseUUID(UUID.randomUUID()), sellerUUID, 0, rawMaterialData, 0));
    }
}
