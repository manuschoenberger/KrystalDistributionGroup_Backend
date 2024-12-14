package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.events.WarehouseActivityData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "activities")
@Getter
@Setter
public class WarehouseActivityJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column (name = "activity_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID activityUUID;

    @Column (name = "warehouse_action")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.VARCHAR)
    private WarehouseActivityData warehouseActivityData;

    @Column (name = "amount")
    @JdbcTypeCode(Types.DOUBLE)
    private double amount;

    @Column (name = "warehouse_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID warehouseUUID;

    @Column (name = "time")
    @JdbcTypeCode(Types.TIMESTAMP)
    private LocalDateTime time;

    @Column(name = "amount_shipped")
    @JdbcTypeCode(Types.DOUBLE)
    private double amountShipped;

    @Column(name = "raw_material_data")
    @Enumerated(EnumType.STRING)
    private RawMaterialData rawMaterialData;

    public WarehouseActivityJpaEntity() {
    }

    public WarehouseActivityJpaEntity(UUID activityUUID) {
        this.activityUUID = activityUUID;
    }
}
