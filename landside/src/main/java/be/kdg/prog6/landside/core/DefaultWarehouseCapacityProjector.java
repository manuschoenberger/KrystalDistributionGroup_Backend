package be.kdg.prog6.landside.core;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.common.events.WarehouseActivityData;
import be.kdg.prog6.landside.domain.Warehouse;
import be.kdg.prog6.landside.ports.in.WarehouseCapacityProjector;
import be.kdg.prog6.landside.ports.out.WarehouseLoadPort;
import be.kdg.prog6.landside.ports.out.WarehouseUpdatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DefaultWarehouseCapacityProjector implements WarehouseCapacityProjector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWarehouseCapacityProjector.class);

    private final WarehouseLoadPort warehouseLoadPort;
    private final WarehouseUpdatePort warehouseUpdatePort;

    public DefaultWarehouseCapacityProjector(
            WarehouseLoadPort warehouseLoadPort,
            WarehouseUpdatePort warehouseUpdatePort
    ) {
        this.warehouseLoadPort = warehouseLoadPort;
        this.warehouseUpdatePort = warehouseUpdatePort;
    }

    @Override
    @Transactional
    public void projectWarehouseCapacity(
            WarehouseUUID warehouseUUID,
            SellerUUID sellerUUID,
            int warehouseNumber,
            RawMaterialData rawMaterialData,
            WarehouseActivityData warehouseActivityData,
            double amount,
            LocalDateTime time
    ) {
        Optional<Warehouse> optionalWarehouse = warehouseLoadPort
                .loadWarehouseByWarehouseUUID(warehouseUUID, sellerUUID, warehouseNumber, rawMaterialData);

        optionalWarehouse.ifPresent(warehouse -> {
            warehouse.modifyWarehouseCapacity(warehouseActivityData, amount);

            LOGGER.info("Updating warehouse {} with {} tons of {}. Activity Action: {}",
                    warehouseUUID.uuid(),
                    amount,
                    warehouse.getRawMaterialData(),
                    warehouseActivityData);

            warehouseUpdatePort.updateWarehouse(warehouse);
        });
    }
}
