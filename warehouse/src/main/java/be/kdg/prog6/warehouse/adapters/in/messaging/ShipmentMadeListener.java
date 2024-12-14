package be.kdg.prog6.warehouse.adapters.in.messaging;

import be.kdg.prog6.common.events.ShipmentMadeEvent;
import be.kdg.prog6.warehouse.ports.in.ShippingRawMaterialUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMadeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentMadeListener.class);
    private static final String SHIPMENT_MADE_QUEUE = "shipment_made";

    private final ShippingRawMaterialUseCase shippingRawMaterialUseCase;

    public ShipmentMadeListener(ShippingRawMaterialUseCase shippingRawMaterialUseCase) {
        this.shippingRawMaterialUseCase = shippingRawMaterialUseCase;
    }

    @RabbitListener(queues = SHIPMENT_MADE_QUEUE)
    public void shipRawMaterial(ShipmentMadeEvent event) {
        LOGGER.info(
                "Shipping for purchase order {} created.",
                event.referenceUUID().uuid()
        );

        shippingRawMaterialUseCase.shipRawMaterial(
                event.referenceUUID()
        );
    }
}
