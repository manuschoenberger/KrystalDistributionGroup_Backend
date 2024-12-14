package be.kdg.prog6.waterside.adapters.out.messaging;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.events.ShipmentMadeEvent;
import be.kdg.prog6.waterside.ports.out.ShipmentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMadePublisher implements ShipmentPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentMadePublisher.class);

    private static final String EXCHANGE_NAME = "waterside_events";

    private final RabbitTemplate rabbitTemplate;

    public ShipmentMadePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void shipmentMade(ReferenceUUID referenceUUID) {
        final String routingKey = "waterside.shipment.made";
        LOGGER.info("Notifying RabbitMQ with routing key: {}", routingKey);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, new ShipmentMadeEvent(
                referenceUUID
        ));
    }
}
