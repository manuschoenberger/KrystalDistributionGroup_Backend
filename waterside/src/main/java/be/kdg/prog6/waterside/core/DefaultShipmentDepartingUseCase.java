package be.kdg.prog6.waterside.core;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.waterside.domain.ShipmentOrder;
import be.kdg.prog6.waterside.domain.ShipmentStatus;
import be.kdg.prog6.waterside.ports.in.ShipmentDepartingUseCase;
import be.kdg.prog6.waterside.ports.out.ShipmentOrderLoadPort;
import be.kdg.prog6.waterside.ports.out.ShipmentOrderUpdatePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultShipmentDepartingUseCase implements ShipmentDepartingUseCase {

    private final ShipmentOrderLoadPort shipmentOrderLoadPort;
    private final ShipmentOrderUpdatePort shipmentOrderUpdatePort;

    public DefaultShipmentDepartingUseCase(ShipmentOrderLoadPort shipmentOrderLoadPort, ShipmentOrderUpdatePort shipmentOrderUpdatePort) {
        this.shipmentOrderLoadPort = shipmentOrderLoadPort;
        this.shipmentOrderUpdatePort = shipmentOrderUpdatePort;
    }

    @Override
    @Transactional
    public void shipmentDeparting(ReferenceUUID referenceUUID) {
        Optional<ShipmentOrder> shipmentOrderOptional = shipmentOrderLoadPort.loadShipmentOrderByReferenceUUID(referenceUUID);

        if (shipmentOrderOptional.isEmpty()) {
            throw new IllegalArgumentException("Shipment order not found");
        }

        ShipmentOrder shipmentOrder = shipmentOrderOptional.get();
        shipmentOrder.setShipmentStatus(ShipmentStatus.DEPARTING);
        shipmentOrderUpdatePort.updateShipmentOrder(shipmentOrder);
    }
}
