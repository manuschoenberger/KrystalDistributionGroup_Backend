package be.kdg.prog6.landside.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "delivery_appointment_timeslots")
@Getter
@Setter
public class DeliveryAppointmentHourTimeslotJpaEntity {

    @Id
    @Column(name = "timeslot_uuid")
    @JdbcTypeCode(value = Types.VARCHAR)
    private UUID timeslotUUID;

    @Column(name = "start_time")
    @JdbcTypeCode(value = Types.TIME)
    private LocalTime startTime;

    @OneToMany(mappedBy = "deliveryAppointmentHourTimeslotJpaEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "delivery_appointments")
    @JdbcTypeCode(value = Types.VARCHAR)
    private List<DeliveryAppointmentJpaEntity> deliveryAppointmentJpaEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "day_scheduler")
    @JdbcTypeCode(value = Types.VARCHAR)
    private DaySchedulerJpaEntity dayScheduler;

    public DeliveryAppointmentHourTimeslotJpaEntity() {
    }

    public DeliveryAppointmentHourTimeslotJpaEntity(UUID timeslotUUID) {
        this.timeslotUUID = timeslotUUID;
    }
}
