package be.kdg.prog6.invoice.adapters.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog = "invoice", name = "commission_fees")
@Getter
@Setter
public class CommissionFeeJpaEntity {
    @Id
    @Column(name = "commission_fee_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID commissionFeeUUID;

    @Column(name = "reference_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID referenceUUID;

    @Column(name = "commission_fee_amount")
    @JdbcTypeCode(Types.DOUBLE)
    private double commissionFeeAmount;

    public CommissionFeeJpaEntity() {
    }

    public CommissionFeeJpaEntity(UUID commissionFeeUUID) {
        this.commissionFeeUUID = commissionFeeUUID;
    }
}
