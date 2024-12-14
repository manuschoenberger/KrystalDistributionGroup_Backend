package be.kdg.prog6.waterside.adapters.in.messaging;

import be.kdg.prog6.common.events.ShipmentReadyEvent;
import be.kdg.prog6.waterside.ports.in.ShipmentDepartingUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShipmentReadyListenerWaterside {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentReadyListenerWaterside.class);
    private static final String SHIPMENT_READY_WATERSIDE_QUEUE = "shipment_ready_waterside";

    private final ShipmentDepartingUseCase shipmentDepartingUseCase;

    public ShipmentReadyListenerWaterside(ShipmentDepartingUseCase shipmentDepartingUseCase) {
        this.shipmentDepartingUseCase = shipmentDepartingUseCase;
    }

    @RabbitListener(queues = SHIPMENT_READY_WATERSIDE_QUEUE)
    public void shipmentDeparting(ShipmentReadyEvent event) {
        LOGGER.info(
                "Shipment for purchase order {} is ready, total material costs {}.",
                event.referenceUUID(),
                event.materialCosts()
        );

        shipmentDepartingUseCase.shipmentDeparting(event.referenceUUID());
    }
}
