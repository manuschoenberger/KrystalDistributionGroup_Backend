package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.landside.domain.PayloadDeliveryTicket;
import be.kdg.prog6.landside.ports.out.PayloadDeliveryTicketUpdatePort;
import org.springframework.stereotype.Repository;

@Repository
public class PayloadDeliveryTicketDBAdapter implements PayloadDeliveryTicketUpdatePort {
    private final PayloadDeliveryTicketRepository payloadDeliveryTicketRepository;

    public PayloadDeliveryTicketDBAdapter(PayloadDeliveryTicketRepository payloadDeliveryTicketRepository) {
        this.payloadDeliveryTicketRepository = payloadDeliveryTicketRepository;
    }

    @Override
    public void updatePayloadDeliveryTicket(PayloadDeliveryTicket payloadDeliveryTicket) {
        PayloadDeliveryTicketJpaEntity payloadDeliveryTicketJpaEntity = new PayloadDeliveryTicketJpaEntity(payloadDeliveryTicket.getPayloadDeliveryTicketUUID().uuid());
        payloadDeliveryTicketJpaEntity.setSellerUUID(payloadDeliveryTicket.getSellerUUID().uuid());
        payloadDeliveryTicketJpaEntity.setPayloadData(payloadDeliveryTicket.getPayloadData());
        payloadDeliveryTicketJpaEntity.setTime(payloadDeliveryTicket.getTime());

        payloadDeliveryTicketRepository.save(payloadDeliveryTicketJpaEntity);
    }
}
