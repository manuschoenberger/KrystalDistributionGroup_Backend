package be.kdg.prog6.waterside.ports.in;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;

public interface ShipmentDepartingUseCase {
    void shipmentDeparting(ReferenceUUID referenceUUID);
}
