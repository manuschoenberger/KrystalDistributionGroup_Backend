package be.kdg.prog6.landside.adapters.out.messaging;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.events.PayloadSubmittedEvent;
import be.kdg.prog6.landside.ports.out.PayloadSubmitPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PayloadSubmittedPublisher implements PayloadSubmitPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayloadSubmittedPublisher.class);
    private static final String EXCHANGE_NAME = "warehouse_events";

    private final RabbitTemplate rabbitTemplate;

    public PayloadSubmittedPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void payloadSubmitted(SellerUUID sellerUUID, RawMaterialData payloadData, double amount) {
        final String routingKey = "payload.submitted";
        LOGGER.info("Notifying RabbitMQ: {}", routingKey);

        rabbitTemplate.convertAndSend(
                EXCHANGE_NAME,
                routingKey,
                new PayloadSubmittedEvent(sellerUUID, payloadData, amount)
        );
    }
}
