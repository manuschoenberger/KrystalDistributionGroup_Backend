package be.kdg.prog6.waterside.ports.out;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;

public interface ShipmentPort {
    void shipmentMade(ReferenceUUID referenceUUID);
}
