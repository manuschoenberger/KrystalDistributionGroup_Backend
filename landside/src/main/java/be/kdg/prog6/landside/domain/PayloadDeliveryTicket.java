package be.kdg.prog6.landside.domain;

import be.kdg.prog6.landside.domain.uuid.PayloadDeliveryTicketUUID;
import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class PayloadDeliveryTicket {
    private final PayloadDeliveryTicketUUID payloadDeliveryTicketUUID;
    private final SellerUUID sellerUUID;
    private final RawMaterialData payloadData;
    private final LocalDateTime time;

    public PayloadDeliveryTicket(SellerUUID sellerUUID, RawMaterialData payloadData, LocalDateTime time) {
        this.payloadDeliveryTicketUUID = new PayloadDeliveryTicketUUID(UUID.randomUUID());
        this.sellerUUID = sellerUUID;
        this.payloadData = payloadData;
        this.time = time;
    }
}
