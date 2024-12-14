package be.kdg.prog6.warehouse.adapters.out.messaging;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.common.events.WarehouseActivityCreatedEvent;
import be.kdg.prog6.warehouse.domain.WarehouseActivity;
import be.kdg.prog6.warehouse.ports.out.WarehouseActivityDeliveryCreatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class WarehouseUpdatePublisherDelivery implements WarehouseActivityDeliveryCreatePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseUpdatePublisherDelivery.class);
    private static final String EXCHANGE_NAME = "warehouse_events";

    private final RabbitTemplate rabbitTemplate;

    public WarehouseUpdatePublisherDelivery(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void createWarehouseActivity(UUID warehouseActivityUUID, WarehouseUUID warehouseUUID, int warehouseNumber, SellerUUID sellerUUID, RawMaterialData rawMaterialData, WarehouseActivity warehouseActivity) {
        final String routingKey = "warehouse." + warehouseUUID.uuid() + ".activity." + warehouseActivity.getWarehouseActivityData() + ".created";
        LOGGER.info("Notifying RabbitMQ: {}", routingKey);

        rabbitTemplate.convertAndSend(
                EXCHANGE_NAME,
                routingKey,
                new WarehouseActivityCreatedEvent(
                        warehouseUUID,
                        sellerUUID,
                        warehouseNumber,
                        rawMaterialData,
                        warehouseActivity.getWarehouseActivityData(),
                        warehouseActivity.getAmount(),
                        LocalDateTime.now()
                )
        );
    }
}
