package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.landside.domain.DeliveryAppointmentStatus;
import be.kdg.prog6.common.domain.RawMaterialData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (catalog = "landside", name = "delivery_appointments")
@Getter
@Setter
public class DeliveryAppointmentJpaEntity {

    @Id
    @Column(name = "delivery_appointment_uuid")
    @JdbcTypeCode(value = Types.VARCHAR)
    private UUID deliveryAppointmentUUID;

    @Column(name = "seller_uuid")
    @JdbcTypeCode(value = Types.VARCHAR)
    private UUID sellerUUID;

    @Column(name = "license_plate")
    @JdbcTypeCode(value = Types.VARCHAR)
    private String licensePlate;

    @Column(name = "payload_data")
    @Enumerated(EnumType.STRING)
    private RawMaterialData payloadData;

    @Column(name = "delivery_appointment_window_start")
    @JdbcTypeCode(value = Types.TIMESTAMP)
    private LocalDateTime deliveryAppointmentWindowStart;

    @Column(name = "delivery_appointment_status")
    @Enumerated(EnumType.STRING)
    private DeliveryAppointmentStatus deliveryAppointmentStatus;

    @ManyToOne
    @JoinColumn(name = "delivery_appointment_hour_timeslot")
    private DeliveryAppointmentHourTimeslotJpaEntity deliveryAppointmentHourTimeslotJpaEntity;

    public DeliveryAppointmentJpaEntity() {
    }

    public DeliveryAppointmentJpaEntity(UUID deliveryAppointmentUUID) {
        this.deliveryAppointmentUUID = deliveryAppointmentUUID;
    }
}
