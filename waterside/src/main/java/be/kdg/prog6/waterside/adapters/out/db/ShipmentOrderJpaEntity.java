package be.kdg.prog6.waterside.adapters.out.db;

import be.kdg.prog6.waterside.domain.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "waterside", name = "shipment_orders")
@Getter
@Setter
public class ShipmentOrderJpaEntity {
    @Id
    @Column(name = "shipment_order_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID shipmentOrderUUID;

    @Column(name = "reference_id")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID referenceUUID;

    @Column(name = "seller_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID sellerUUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipment_status")
    private ShipmentStatus shipmentStatus;

    @Column(name = "vessel_number")
    @JdbcTypeCode(Types.VARCHAR)
    private String vesselNumber;

    @Column(name = "estimated_arrival_time")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime estimatedArrivalTime;

    @Column(name = "estimated_departure_time")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime estimatedDepartureTime;

    @Column(name = "bunkering_operation_time")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime bunkeringOperationTime;

    @Column(name = "inspection_operation_time")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime inspectionOperationTime;

    @Column(name = "inspection_signature")
    @JdbcTypeCode(Types.VARCHAR)
    private String inspectionSignature;

    public ShipmentOrderJpaEntity(UUID shipmentOrderUUID) {
        this.shipmentOrderUUID = shipmentOrderUUID;
    }

    public ShipmentOrderJpaEntity() {
    }
}
