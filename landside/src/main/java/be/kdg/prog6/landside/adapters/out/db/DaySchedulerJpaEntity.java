package be.kdg.prog6.landside.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "day_scheduler")
@Getter
@Setter
public class DaySchedulerJpaEntity {

    @Id
    @Column(name = "day_scheduler_uuid")
    @JdbcTypeCode(value = Types.VARCHAR)
    private UUID daySchedulerUUID;

    @Column(name = "date")
    @JdbcTypeCode(value = Types.DATE)
    private LocalDate date;

    @OneToMany(mappedBy = "dayScheduler", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "delivery_appointment_hour_timeslots")
    @JdbcTypeCode(value = Types.VARCHAR)
    private List<DeliveryAppointmentHourTimeslotJpaEntity> deliveryAppointmentHourTimeslotJpaEntities = new ArrayList<>();

    public DaySchedulerJpaEntity() {
    }

    public DaySchedulerJpaEntity(UUID daySchedulerUUID) {
        this.daySchedulerUUID = daySchedulerUUID;
    }
}
