package be.kdg.prog6.landside.adapters.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "weighing_bridge_tickets")
@Getter
@Setter
public class WeighingBridgeTicketJpaEntity {
    @Id
    @Column(name = "weighing_bridge_ticket_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID weighingBridgeTicketUUID;

    @Column(name = "license_plate")
    @JdbcTypeCode(Types.VARCHAR)
    private String licensePlate;

    @Column(name = "gross_weight")
    @JdbcTypeCode(Types.DOUBLE)
    private double grossWeight;

    @Column(name = "tare_weight")
    @JdbcTypeCode(Types.DOUBLE)
    private double tareWeight;

    @Column(name = "net_weight")
    @JdbcTypeCode(Types.DOUBLE)
    private double netWeight;

    @Column(name = "arrival_timestamp")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime arrivalTimestamp;

    @Column(name = "exit_timestamp")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime exitTimestamp;

    public WeighingBridgeTicketJpaEntity(UUID weighingBridgeTicketUUID) {
        this.weighingBridgeTicketUUID = weighingBridgeTicketUUID;
    }

    public WeighingBridgeTicketJpaEntity() {
    }
}
