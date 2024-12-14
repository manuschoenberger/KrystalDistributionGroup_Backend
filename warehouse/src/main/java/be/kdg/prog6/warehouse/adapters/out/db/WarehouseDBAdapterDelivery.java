package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.warehouse.domain.uuid.WarehouseActivityUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.warehouse.domain.ActivityWindow;
import be.kdg.prog6.warehouse.domain.Warehouse;
import be.kdg.prog6.warehouse.domain.WarehouseActivity;
import be.kdg.prog6.warehouse.ports.out.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WarehouseDBAdapterDelivery implements WarehouseUpdatePort, WarehouseLoadPort, WarehouseActivityDeliveryCreatePort, WarehouseActivityLoadPort, WarehouseActivityShipmentCreatePort {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseActivityRepository warehouseActivityRepository;

    public WarehouseDBAdapterDelivery(WarehouseRepository warehouseRepository, WarehouseActivityRepository warehouseActivityRepository) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseActivityRepository = warehouseActivityRepository;
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        WarehouseJpaEntity warehouseJpaEntity = new WarehouseJpaEntity(warehouse.getWarehouseUUID().uuid());
        warehouseJpaEntity.setWarehouseNumber(warehouse.getWarehouseNumber());
        warehouseJpaEntity.setSellerUUID(warehouse.getSellerUUID().uuid());
        warehouseJpaEntity.setRawMaterialData(warehouse.getRawMaterialData());

        warehouseRepository.save(warehouseJpaEntity);
    }

    @Override
    public Optional<Warehouse> loadWarehouseBySellerAndRawMaterialData(SellerUUID sellerUUID, RawMaterialData rawMaterialData) {
        Optional<WarehouseJpaEntity> warehouseJpaEntity = warehouseRepository
                .findWarehouseBySellerUUIDAndRawMaterialData(sellerUUID.uuid(), rawMaterialData);

        return warehouseJpaEntity.map(this::mapToWarehouse);

    }

    @Override
    public void createWarehouseActivity(UUID warehouseActivityUUID, WarehouseUUID warehouseUUID, int warehouseNumber, SellerUUID sellerUUID, RawMaterialData rawMaterialData, WarehouseActivity warehouseActivity) {
        WarehouseActivityJpaEntity warehouseActivityJpaEntity = new WarehouseActivityJpaEntity(warehouseActivityUUID);
        warehouseActivityJpaEntity.setWarehouseActivityData(warehouseActivity.getWarehouseActivityData());
        warehouseActivityJpaEntity.setAmount(warehouseActivity.getAmount());
        warehouseActivityJpaEntity.setTime(warehouseActivity.getTime());
        warehouseActivityJpaEntity.setWarehouseUUID(warehouseUUID.uuid());
        warehouseActivityJpaEntity.setAmountShipped(warehouseActivity.getAmountShipped());
        warehouseActivityJpaEntity.setRawMaterialData(rawMaterialData);

        warehouseActivityRepository.save(warehouseActivityJpaEntity);
    }

    @Override
    public Optional<Warehouse> loadWarehouseByWarehouseUUID(WarehouseUUID warehouseUUID) {
        Optional<WarehouseJpaEntity> warehouseJpaEntity = warehouseRepository.findWarehouseByWarehouseUUID(warehouseUUID.uuid());

        return warehouseJpaEntity.map(this::mapToWarehouse);

    }

    @Override
    public Optional<List<Warehouse>> loadWarehousesBySellerUUID(SellerUUID sellerUUID) {
        Optional<List<WarehouseJpaEntity>> warehouseJpaEntities = warehouseRepository.findWarehousesBySellerUUID(sellerUUID.uuid());

        if (warehouseJpaEntities.isEmpty()) {
            return Optional.empty();
        }

        List<Warehouse> warehouses = new ArrayList<>();

        for (WarehouseJpaEntity warehouseJpaEntity : warehouseJpaEntities.get()) {
            Warehouse warehouse = mapToWarehouse(warehouseJpaEntity);
            warehouses.add(warehouse);
        }

        return Optional.of(warehouses);
    }

    @Override
    public Optional<List<Warehouse>> loadAllWarehouses() {
        List<WarehouseJpaEntity> warehouseJpaEntities = warehouseRepository.findAll();

        if (warehouseJpaEntities.isEmpty()) {
            return Optional.empty();
        }

        List<Warehouse> warehouses = new ArrayList<>();

        for (WarehouseJpaEntity warehouseJpaEntity : warehouseJpaEntities) {
            Warehouse warehouse = mapToWarehouse(warehouseJpaEntity);
            warehouses.add(warehouse);
        }

        return Optional.of(warehouses);
    }

    private Warehouse mapToWarehouse(WarehouseJpaEntity warehouseJpaEntity) {
        Warehouse warehouse = new Warehouse(
                new WarehouseUUID(warehouseJpaEntity.getWarehouseUUID()),
                warehouseJpaEntity.getWarehouseNumber(),
                warehouseJpaEntity.getRawMaterialData(),
                new ActivityWindow(),
                new SellerUUID(warehouseJpaEntity.getSellerUUID())
        );

        List<WarehouseActivityJpaEntity> warehouseActivityJpaEntities = warehouseActivityRepository
                .findByWarehouseUUID(warehouse.getWarehouseUUID().uuid());

        for (WarehouseActivityJpaEntity warehouseActivityJpaEntity : warehouseActivityJpaEntities) {
            warehouse.addWarehouseActivity(new WarehouseActivity(
                    new WarehouseActivityUUID(warehouseActivityJpaEntity.getActivityUUID()),
                    warehouseActivityJpaEntity.getWarehouseActivityData(),
                    warehouseActivityJpaEntity.getAmount(),
                    warehouseActivityJpaEntity.getTime(),
                    warehouseActivityJpaEntity.getAmountShipped(),
                    warehouse.getRawMaterialData()
            ));
        }

        return warehouse;
    }

    @Override
    public Optional<List<WarehouseActivity>> loadAllWarehouseActivities() {
        List<WarehouseActivityJpaEntity> warehouseActivityJpaEntities = warehouseActivityRepository.findAll();

        return warehouseActivityJpaEntities.isEmpty() ? Optional.empty() : Optional.of(mapToWarehouseActivities(warehouseActivityJpaEntities));
    }

    private List<WarehouseActivity> mapToWarehouseActivities(List<WarehouseActivityJpaEntity> warehouseActivityJpaEntities) {
        List<WarehouseActivity> warehouseActivities = new ArrayList<>();

        for (WarehouseActivityJpaEntity warehouseActivityJpaEntity : warehouseActivityJpaEntities) {
            WarehouseActivity warehouseActivity = new WarehouseActivity(
                    new WarehouseActivityUUID(warehouseActivityJpaEntity.getActivityUUID()),
                    warehouseActivityJpaEntity.getWarehouseActivityData(),
                    warehouseActivityJpaEntity.getAmount(),
                    warehouseActivityJpaEntity.getTime(),
                    warehouseActivityJpaEntity.getAmountShipped(),
                    warehouseActivityJpaEntity.getRawMaterialData()
            );

            warehouseActivities.add(warehouseActivity);
        }

        return warehouseActivities;
    }

    @Override
    public void createWarehouseActivity(UUID warehouseActivityUUID, double amountShipped) {

        Optional<WarehouseActivityJpaEntity> warehouseActivityJpaEntity = warehouseActivityRepository.findById(warehouseActivityUUID);

        if (warehouseActivityJpaEntity.isEmpty()) {
            throw new IllegalArgumentException("Warehouse activity not found");
        }

        WarehouseActivityJpaEntity warehouseActivity = warehouseActivityJpaEntity.get();
        warehouseActivity.setAmountShipped(amountShipped);
        warehouseActivityRepository.save(warehouseActivity);
    }
}
