package be.kdg.prog6.invoice.adapters.in.messaging;

import be.kdg.prog6.common.events.ShipmentReadyEvent;
import be.kdg.prog6.invoice.ports.in.CommissionFeeCalculationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShipmentReadyListenerInvoice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentReadyListenerInvoice.class);
    private static final String SHIPMENT_READY_INVOICE_QUEUE = "shipment_ready_invoice";

    private final CommissionFeeCalculationUseCase commissionFeeCalculationUseCase;

    public ShipmentReadyListenerInvoice(CommissionFeeCalculationUseCase commissionFeeCalculationUseCase) {
        this.commissionFeeCalculationUseCase = commissionFeeCalculationUseCase;
    }

    @RabbitListener(queues = SHIPMENT_READY_INVOICE_QUEUE)
    public void calculateCommissionFee(ShipmentReadyEvent event) {
        LOGGER.info(
                "Shipping for purchase order {} is ready, total material costs {}. Calculating commission fee...",
                event.referenceUUID(),
                event.materialCosts()
        );

        commissionFeeCalculationUseCase.computeCommissionFee(event.referenceUUID(), event.materialCosts());
    }
}

