package be.kdg.prog6.warehouse.adapters.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class PurchaseOrderPartyJpaEntity {
    @Column(name = "party_uuid")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID partyUUID;

    @Column(name = "party_name")
    @JdbcTypeCode(Types.VARCHAR)
    private String name;

    @Column(name = "party_address")
    @JdbcTypeCode(Types.VARCHAR)
    private String address;

    public PurchaseOrderPartyJpaEntity() {
    }
}

