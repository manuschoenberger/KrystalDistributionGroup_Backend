package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "warehouseprojections")
@Getter
@Setter
public class WarehouseProjectionJpaEntity {

    @Id
    @Column(name = "warehouse_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID warehouseUUID;

    @Column(name = "seller_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID sellerUUID;

    @Column(name = "warehouse_number")
    @JdbcTypeCode(Types.INTEGER)
    private int warehouseNumber;

    @Column(name = "raw_material_data")
    @Enumerated(EnumType.STRING)
    private RawMaterialData rawMaterialData;

    @Column(name = "current_capacity")
    @JdbcTypeCode(Types.DOUBLE)
    private double currentCapacity;

    public WarehouseProjectionJpaEntity() {
    }

    public WarehouseProjectionJpaEntity(UUID warehouseUUID) {
        this.warehouseUUID = warehouseUUID;
    }
}
