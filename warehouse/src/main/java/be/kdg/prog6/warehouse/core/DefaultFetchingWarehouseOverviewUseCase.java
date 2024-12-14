package be.kdg.prog6.warehouse.core;

import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.warehouse.domain.Warehouse;
import be.kdg.prog6.warehouse.domain.WarehouseOverview;
import be.kdg.prog6.warehouse.ports.in.FetchWarehouseOverviewBySellerUUIDCommand;
import be.kdg.prog6.warehouse.ports.in.FetchWarehouseOverviewByWarehouseUUIDCommand;
import be.kdg.prog6.warehouse.ports.in.FetchingWarehouseOverviewUseCase;
import be.kdg.prog6.warehouse.ports.out.WarehouseLoadPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultFetchingWarehouseOverviewUseCase implements FetchingWarehouseOverviewUseCase {

    private final WarehouseLoadPort warehouseLoadPort;

    public DefaultFetchingWarehouseOverviewUseCase(WarehouseLoadPort warehouseLoadPort) {
        this.warehouseLoadPort = warehouseLoadPort;
    }


    @Override
    @Transactional
    public WarehouseOverview fetchWarehouseOverviewByWarehouseUUID(FetchWarehouseOverviewByWarehouseUUIDCommand fetchWarehouseOverviewByWarehouseUUIDCommand) {
        Warehouse warehouses = warehouseLoadPort.loadWarehouseByWarehouseUUID(new WarehouseUUID(fetchWarehouseOverviewByWarehouseUUIDCommand.warehouseUUID().uuid()))
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for UUID: " + fetchWarehouseOverviewByWarehouseUUIDCommand.warehouseUUID().uuid()));

        return new WarehouseOverview(
                warehouses.getWarehouseUUID(),
                warehouses.getWarehouseNumber(),
                warehouses.getRawMaterialData(),
                warehouses.getSellerUUID(),
                warehouses.getActivityWindow(),
                warehouses.computeCapacity()
        );
    }

    @Override
    public List<WarehouseOverview> fetchWarehouseOverviewBySellerUUID(FetchWarehouseOverviewBySellerUUIDCommand fetchWarehouseOverviewBySellerUUIDCommand) {
        Optional<List<Warehouse>> warehousesOptional = warehouseLoadPort.loadWarehousesBySellerUUID(fetchWarehouseOverviewBySellerUUIDCommand.sellerUUID());

        return warehousesOptional.map(warehouses -> warehouses.stream()
                .map(warehouse -> new WarehouseOverview(
                        warehouse.getWarehouseUUID(),
                        warehouse.getWarehouseNumber(),
                        warehouse.getRawMaterialData(),
                        warehouse.getSellerUUID(),
                        warehouse.getActivityWindow(),
                        warehouse.computeCapacity()
                ))
                .toList()).orElseGet(List::of);
    }

    @Override
    public List<WarehouseOverview> fetchWarehouseOverview() {
        Optional<List<Warehouse>> warehousesOptional = warehouseLoadPort.loadAllWarehouses();

        return warehousesOptional.map(warehouses -> warehouses.stream()
                .map(warehouse -> new WarehouseOverview(
                        warehouse.getWarehouseUUID(),
                        warehouse.getWarehouseNumber(),
                        warehouse.getRawMaterialData(),
                        warehouse.getSellerUUID(),
                        warehouse.getActivityWindow(),
                        warehouse.computeCapacity()
                ))
                .toList()).orElseGet(List::of);
    }
}
