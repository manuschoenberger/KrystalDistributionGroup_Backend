package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "raw_materials")
@Getter
@Setter
public class RawMaterialJpaEntity {
    @Id
    @Column(name = "raw_material_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID rawMaterialUUID;

    @Column(name = "raw_material_data")
    @Enumerated(EnumType.STRING)
    private RawMaterialData rawMaterialData;

    @Column(name = "description")
    @JdbcTypeCode(Types.VARCHAR)
    private String description;

    @Column(name = "storage_price_per_ton_per_day")
    @JdbcTypeCode(Types.DOUBLE)
    private double storagePricePerTonPerDay;

    @Column(name = "price_per_ton")
    @JdbcTypeCode(Types.DOUBLE)
    private double pricePerTon;

    public RawMaterialJpaEntity() {
    }

    public RawMaterialJpaEntity(UUID rawMaterialUUID) {
        this.rawMaterialUUID = rawMaterialUUID;
    }
}
