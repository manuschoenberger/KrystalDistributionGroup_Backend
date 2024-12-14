package be.kdg.prog6.landside.core;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.landside.domain.Warehouse;
import be.kdg.prog6.landside.ports.in.WarehouseCreationProjector;
import be.kdg.prog6.landside.ports.out.WarehouseUpdatePort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultWarehouseCreatedProjector implements WarehouseCreationProjector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWarehouseCreatedProjector.class);

    private final WarehouseUpdatePort warehouseUpdatePort;

    public DefaultWarehouseCreatedProjector(WarehouseUpdatePort warehouseUpdatePort) {
        this.warehouseUpdatePort = warehouseUpdatePort;
    }

    @Override
    @Transactional
    public void projectWarehouseCreation(
            WarehouseUUID warehouseUUID,
            SellerUUID sellerUUID,
            RawMaterialData rawMaterialData
    ) {
        LOGGER.info("Creating warehouse {} in landside context for seller {} and the payload {}",
                warehouseUUID.uuid(),
                sellerUUID.uuid(),
                rawMaterialData);

        warehouseUpdatePort.updateWarehouse(new Warehouse(warehouseUUID, sellerUUID, 0, rawMaterialData, 0));
    }
}
