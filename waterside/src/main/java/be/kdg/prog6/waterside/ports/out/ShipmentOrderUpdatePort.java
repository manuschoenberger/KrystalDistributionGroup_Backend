package be.kdg.prog6.waterside.ports.out;

import be.kdg.prog6.waterside.domain.ShipmentOrder;

public interface ShipmentOrderUpdatePort {
    void updateShipmentOrder(ShipmentOrder shipmentOrder);
}
