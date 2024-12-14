package be.kdg.prog6.warehouse.adapters.in.messaging;

import be.kdg.prog6.common.events.PayloadSubmittedEvent;
import be.kdg.prog6.warehouse.ports.in.DeliverRawMaterialCommand;
import be.kdg.prog6.warehouse.ports.in.DeliveringRawMaterialUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RawMaterialDeliveredListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawMaterialDeliveredListener.class);
    private static final String PAYLOAD_SUBMITTED_QUEUE = "payload_submitted";

    private final DeliveringRawMaterialUseCase deliveringRawMaterialUseCase;

    public RawMaterialDeliveredListener(DeliveringRawMaterialUseCase deliveringRawMaterialUseCase) {
        this.deliveringRawMaterialUseCase = deliveringRawMaterialUseCase;
    }

    @RabbitListener(queues = PAYLOAD_SUBMITTED_QUEUE)
    public void deliverRawMaterial(PayloadSubmittedEvent event) {
        LOGGER.info(
                "Delivery of {} tons of {} for {} created.",
                event.amount(),
                event.rawMaterialData(),
                event.sellerUUID()
        );

        deliveringRawMaterialUseCase.deliverRawMaterial((new DeliverRawMaterialCommand(event.amount(), event.sellerUUID(), event.rawMaterialData())));
    }
}
