package be.kdg.prog6.landside.adapters.in.messaging;

import be.kdg.prog6.common.events.WarehouseContextReceivedWarehouseEvent;
import be.kdg.prog6.landside.ports.in.WarehouseCreationProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WarehouseReceivedWarehouseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseReceivedWarehouseListener.class);
    private static final String RECEIVE_WAREHOUSES_QUEUE = "warehouse_receives";

    private final WarehouseCreationProjector warehouseCreationProjector;

    public WarehouseReceivedWarehouseListener(WarehouseCreationProjector warehouseCreationProjector) {
        this.warehouseCreationProjector = warehouseCreationProjector;
    }
/*
    @RabbitListener(queues = RECEIVE_WAREHOUSES_QUEUE)
    public void createWarehouse(WarehouseContextReceivedWarehouseEvent warehouseContextReceivedWarehouseEvent) {
        LOGGER.info(
                "Warehouse {} for seller {} created.",
                warehouseContextReceivedWarehouseEvent.warehouseUUID(),
                warehouseContextReceivedWarehouseEvent.sellerUUID()
        );

        warehouseCreationProjector.projectWarehouseCreation(
                warehouseContextReceivedWarehouseEvent.warehouseUUID(),
                warehouseContextReceivedWarehouseEvent.sellerUUID(),
                warehouseContextReceivedWarehouseEvent.rawMaterialData()
        );
    }
 */
}
