package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Table(catalog = "warehouse", name = "purchase_order_lines")
@Getter
@Setter
public class PurchaseOrderLineJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(Types.BIGINT)
    private Long id;

    @Column(name = "line_number")
    @JdbcTypeCode(Types.INTEGER)
    private int lineNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type")
    private RawMaterialData materialType;

    @Column(name = "description")
    @JdbcTypeCode(Types.VARCHAR)
    private String description;

    @Column(name = "quantity")
    @JdbcTypeCode(Types.INTEGER)
    private int quantity;

    @Column(name = "uom")
    @JdbcTypeCode(Types.VARCHAR)
    private String uom;


    public PurchaseOrderLineJpaEntity() {
    }
}
