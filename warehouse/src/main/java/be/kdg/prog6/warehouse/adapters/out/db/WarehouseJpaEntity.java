package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "warehouses")
@Getter
@Setter
public class WarehouseJpaEntity {

    @Id
    @Column(name = "warehouse_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID warehouseUUID;

    @Column(name = "warehouse_number")
    @JdbcTypeCode(Types.VARCHAR)
    private int warehouseNumber;

    @Column(name = "raw_material_data")
    @Enumerated(EnumType.STRING)
    private RawMaterialData rawMaterialData;

    @Column(name = "seller_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID sellerUUID;

    public WarehouseJpaEntity() {
    }

    public WarehouseJpaEntity(UUID warehouseUUID) {
        this.warehouseUUID = warehouseUUID;
    }
}
