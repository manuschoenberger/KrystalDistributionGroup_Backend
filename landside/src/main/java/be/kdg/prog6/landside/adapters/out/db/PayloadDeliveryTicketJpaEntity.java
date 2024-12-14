package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "payload_delivery_tickets")
@Getter
@Setter
public class PayloadDeliveryTicketJpaEntity {

    @Id
    @Column(name = "payload_delivery_ticket_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID payloadDeliveryTicketUUID;

    @Column(name = "seller_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID sellerUUID;

    @Column(name = "payload_data")
    @Enumerated(EnumType.STRING)
    private RawMaterialData payloadData;

    @Column(name = "time")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime time;

    public PayloadDeliveryTicketJpaEntity() {
    }

    public PayloadDeliveryTicketJpaEntity(UUID payloadDeliveryTicketUUID) {
        this.payloadDeliveryTicketUUID = payloadDeliveryTicketUUID;
    }
}
