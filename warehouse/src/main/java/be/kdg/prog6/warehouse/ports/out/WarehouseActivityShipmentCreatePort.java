package be.kdg.prog6.warehouse.ports.out;

import java.util.UUID;

public interface WarehouseActivityShipmentCreatePort {
    void createWarehouseActivity(UUID warehouseActivityUUID, double amountShipped);
}
