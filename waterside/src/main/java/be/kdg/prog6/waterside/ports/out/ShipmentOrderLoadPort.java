package be.kdg.prog6.waterside.ports.out;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.waterside.domain.ShipmentOrder;

import java.util.Optional;

public interface ShipmentOrderLoadPort {
    Optional<ShipmentOrder> loadShipmentOrderByReferenceUUID(ReferenceUUID referenceUUID);
}
