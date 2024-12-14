
package be.kdg.prog6.warehouse.ports.out;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;

public interface ShipmentReadyPort {
    void shipmentReady(ReferenceUUID referenceUUID, double materialCosts);
}
