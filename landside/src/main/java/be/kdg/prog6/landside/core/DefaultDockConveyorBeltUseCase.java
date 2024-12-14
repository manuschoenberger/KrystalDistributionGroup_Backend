package be.kdg.prog6.landside.core;

import be.kdg.prog6.landside.domain.PayloadDeliveryTicket;
import be.kdg.prog6.landside.domain.Truck;
import be.kdg.prog6.landside.domain.TruckStatus;
import be.kdg.prog6.landside.ports.in.DockConveyorBeltCommand;
import be.kdg.prog6.landside.ports.in.DockingConveyorBeltUseCase;
import be.kdg.prog6.landside.ports.out.PayloadDeliveryTicketUpdatePort;
import be.kdg.prog6.landside.ports.out.TruckLoadPort;
import be.kdg.prog6.landside.ports.out.TruckUpdatePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class DefaultDockConveyorBeltUseCase implements DockingConveyorBeltUseCase {

    private final PayloadDeliveryTicketUpdatePort payloadDeliveryTicketUpdatePort;
    private final TruckLoadPort truckLoadPort;
    private final TruckUpdatePort truckUpdatePort;
    private final Random random;

    public DefaultDockConveyorBeltUseCase(PayloadDeliveryTicketUpdatePort payloadDeliveryTicketUpdatePort, TruckLoadPort truckLoadPort, TruckUpdatePort truckUpdatePort) {
        this.payloadDeliveryTicketUpdatePort = payloadDeliveryTicketUpdatePort;
        this.truckLoadPort = truckLoadPort;
        this.truckUpdatePort = truckUpdatePort;
        this.random = new Random();
    }

    @Override
    @Transactional
    public String dockConveyorBelt(DockConveyorBeltCommand dockConveyorBeltCommand) {

        PayloadDeliveryTicket payloadDeliveryTicket = new PayloadDeliveryTicket(dockConveyorBeltCommand.sellerUUID(), dockConveyorBeltCommand.payloadData(), LocalDateTime.now());
        payloadDeliveryTicketUpdatePort.updatePayloadDeliveryTicket(payloadDeliveryTicket);

        int weighingBridgeNumber = random.nextInt(5) + 1;

        Truck truck = truckLoadPort.loadTruckByLicensePlate(dockConveyorBeltCommand.licensePlate())
                .orElseThrow(() -> new IllegalArgumentException("No truck found for the given license plate: " + dockConveyorBeltCommand.licensePlate().licensePlate()));
        truck.setTruckStatus(TruckStatus.WEIGHING_BRIDGE_EXIT);

        truckUpdatePort.updateTruck(truck);

        return "Docked to conveyor belt at " + payloadDeliveryTicket.getTime() + " with payload data: " + payloadDeliveryTicket.getPayloadData().name() + ". Go to weighing bridge " + weighingBridgeNumber;
    }
}
