package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.landside.domain.TruckStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "trucks")
@Getter
@Setter
public class TruckJpaEntity {
    @Id
    @Column(name = "truck_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID truckUUID;

    @Column(name = "license_plate")
    @JdbcTypeCode(Types.VARCHAR)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "truck_status")
    private TruckStatus truckStatus;

    public TruckJpaEntity(UUID truckUUID) {
        this.truckUUID = truckUUID;
    }

    public TruckJpaEntity() {
    }
}
