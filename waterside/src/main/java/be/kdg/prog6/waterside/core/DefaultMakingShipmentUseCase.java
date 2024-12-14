package be.kdg.prog6.waterside.core;

import be.kdg.prog6.waterside.domain.ShipmentOrder;
import be.kdg.prog6.waterside.ports.in.MakeShipmentCommand;
import be.kdg.prog6.waterside.ports.in.MakingShipmentUseCase;
import be.kdg.prog6.waterside.ports.out.ShipmentOrderUpdatePort;
import be.kdg.prog6.waterside.ports.out.ShipmentPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DefaultMakingShipmentUseCase implements MakingShipmentUseCase {
    private final ShipmentPort shippingPort;
    private final ShipmentOrderUpdatePort shippingOrderUpdatePort;

    public DefaultMakingShipmentUseCase(ShipmentPort shippingPort, ShipmentOrderUpdatePort shippingOrderUpdatePort) {
        this.shippingPort = shippingPort;
        this.shippingOrderUpdatePort = shippingOrderUpdatePort;
    }

    @Override
    @Transactional
    public String makeShipment(MakeShipmentCommand makeShipmentCommand) {
        ShipmentOrder shipmentOrder = new ShipmentOrder(
                makeShipmentCommand.sellerUUID(),
                makeShipmentCommand.referenceUUID(),
                makeShipmentCommand.vesselNumber()
        );

        String bunkeringResult = makeBunkeringOperation(shipmentOrder);
        String inspectionResult = makeInspectionOperation(shipmentOrder);
        shippingOrderUpdatePort.updateShipmentOrder(shipmentOrder);

        shippingPort.shipmentMade(
                makeShipmentCommand.referenceUUID()
        );

        return bunkeringResult + "; " + inspectionResult;
    }

    private String makeBunkeringOperation(ShipmentOrder shipmentOrder) {
        ShipmentOrder.BunkeringOperation bunkeringOperation = new ShipmentOrder.BunkeringOperation(
                shipmentOrder.getBunkeringOperation().bunkeringOperationTime()
        );

        shipmentOrder.setBunkeringOperation(bunkeringOperation);

        return "Bunkering operation made";
    }

    private String makeInspectionOperation(ShipmentOrder shipmentOrder) {
        ShipmentOrder.InspectionOperation inspectionOperation = new ShipmentOrder.InspectionOperation(
                shipmentOrder.getInspectionOperation().inspectionOperationTime(),
                "SIGNATURE"
        );

        shipmentOrder.setInspectionOperation(inspectionOperation);

        return "Inspection operation made";
    }
}
