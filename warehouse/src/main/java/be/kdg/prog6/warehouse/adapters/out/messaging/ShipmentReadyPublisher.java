package be.kdg.prog6.warehouse.adapters.out.messaging;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.events.ShipmentReadyEvent;
import be.kdg.prog6.warehouse.ports.out.ShipmentReadyPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShipmentReadyPublisher implements ShipmentReadyPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentReadyPublisher.class);

    private static final String EXCHANGE_NAME = "warehouse_events";

    private final RabbitTemplate rabbitTemplate;

    public ShipmentReadyPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void shipmentReady(ReferenceUUID referenceUUID, double materialCosts) {
        final String routingKey = "invoice." + referenceUUID + ".shipment.ready";
        LOGGER.info("Notifying RabbitMQ: {}", routingKey);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, new ShipmentReadyEvent(
                referenceUUID,
                materialCosts
        ));
    }
}
