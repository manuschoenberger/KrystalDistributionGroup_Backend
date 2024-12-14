package be.kdg.prog6.landside.ports.out;

import be.kdg.prog6.landside.domain.PayloadDeliveryTicket;

public interface PayloadDeliveryTicketUpdatePort {
    void updatePayloadDeliveryTicket(PayloadDeliveryTicket payloadDeliveryTicket);
}
