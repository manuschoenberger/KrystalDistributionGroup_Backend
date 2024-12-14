package be.kdg.prog6.warehouse.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "purchase_orders")
@Getter
@Setter
public class PurchaseOrderJpaEntity {
    @Column(name = "po_number")
    @JdbcTypeCode(Types.VARCHAR)
    private String poNumber;

    @Id
    @Column(name = "reference_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID referenceUUID;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "partyUUID", column = @Column(name = "customer_party_uuid")),
            @AttributeOverride(name = "name", column = @Column(name = "customer_party_name")),
            @AttributeOverride(name = "address", column = @Column(name = "customer_party_address"))
    })
    private PurchaseOrderPartyJpaEntity customerParty;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "partyUUID", column = @Column(name = "seller_party_uuid")),
            @AttributeOverride(name = "name", column = @Column(name = "seller_party_name")),
            @AttributeOverride(name = "address", column = @Column(name = "seller_party_address"))
    })
    private PurchaseOrderPartyJpaEntity sellerParty;

    @Column(name = "vessel_number")
    @JdbcTypeCode(Types.VARCHAR)
    private String vesselNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_order_lines")
    private List<PurchaseOrderLineJpaEntity> orderLines;

    @Column(name = "arrived")
    @JdbcTypeCode(Types.BOOLEAN)
    private boolean arrived;

    @Column(name = "total_amount")
    @JdbcTypeCode(Types.DOUBLE)
    private double totalAmount;

    public PurchaseOrderJpaEntity() {
    }

    public PurchaseOrderJpaEntity(UUID referenceUUID) {
        this.referenceUUID = referenceUUID;
    }
}
