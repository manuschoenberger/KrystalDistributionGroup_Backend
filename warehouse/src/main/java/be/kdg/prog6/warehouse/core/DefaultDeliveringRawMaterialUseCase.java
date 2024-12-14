package be.kdg.prog6.warehouse.core;

import be.kdg.prog6.warehouse.domain.RawMaterial;
import be.kdg.prog6.warehouse.domain.Warehouse;
import be.kdg.prog6.warehouse.domain.WarehouseActivity;
import be.kdg.prog6.warehouse.ports.in.DeliverRawMaterialCommand;
import be.kdg.prog6.warehouse.ports.in.DeliveringRawMaterialUseCase;
import be.kdg.prog6.warehouse.ports.out.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultDeliveringRawMaterialUseCase implements DeliveringRawMaterialUseCase {

    private final WarehouseUpdatePort warehouseUpdatePort;
    private final List<WarehouseActivityDeliveryCreatePort> warehouseActivityDeliveryCreatePorts;
    private final WarehouseLoadPort warehouseLoadPort;
    private final RawMaterialLoadPort rawMaterialLoadPort;

    public DefaultDeliveringRawMaterialUseCase(
            WarehouseUpdatePort warehouseUpdatePort,
            List<WarehouseActivityDeliveryCreatePort> warehouseActivityDeliveryCreatePorts,
            WarehouseLoadPort warehouseLoadPort,
            RawMaterialLoadPort rawMaterialLoadPort)
    {
        this.warehouseUpdatePort = warehouseUpdatePort;
        this.warehouseActivityDeliveryCreatePorts = warehouseActivityDeliveryCreatePorts;
        this.warehouseLoadPort = warehouseLoadPort;
        this.rawMaterialLoadPort = rawMaterialLoadPort;
    }

    @Override
    @Transactional
    public void deliverRawMaterial(DeliverRawMaterialCommand deliverRawMaterialCommand) {
        Optional<RawMaterial> optionalRawMaterial = rawMaterialLoadPort.loadRawMaterialByRawMaterialData(
                deliverRawMaterialCommand.rawMaterialData());

        if (optionalRawMaterial.isEmpty()) {
            throw new IllegalArgumentException("Raw material not found: " + deliverRawMaterialCommand.rawMaterialData());
        }

        RawMaterial rawMaterial = optionalRawMaterial.get();

        Optional<Warehouse> optionalWarehouse = warehouseLoadPort.loadWarehouseBySellerAndRawMaterialData(
                deliverRawMaterialCommand.sellerUUID(), deliverRawMaterialCommand.rawMaterialData());

        Warehouse warehouse;

        if (optionalWarehouse.isEmpty()) {
            warehouse = new Warehouse(rawMaterial.rawMaterialData(), deliverRawMaterialCommand.sellerUUID());
            warehouseUpdatePort.updateWarehouse(warehouse);
        } else {
            warehouse = optionalWarehouse.get();
        }

        WarehouseActivity warehouseActivity = warehouse.deliverRawMaterial(deliverRawMaterialCommand.amount(), deliverRawMaterialCommand.rawMaterialData());
        warehouseActivityDeliveryCreatePorts.forEach(
                warehouseActivityDeliveryCreatePort -> warehouseActivityDeliveryCreatePort.createWarehouseActivity(
                        UUID.randomUUID(),
                        warehouse.getWarehouseUUID(),
                        warehouse.getWarehouseNumber(),
                        warehouse.getSellerUUID(),
                        warehouse.getRawMaterialData(),
                        warehouseActivity));

    }
}
