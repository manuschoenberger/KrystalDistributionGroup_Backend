package be.kdg.prog6.invoice.domain;

import be.kdg.prog6.invoice.domain.uuid.CommissionFeeUUID;
import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommissionFee {
    private CommissionFeeUUID commissionFeeId;
    private ReferenceUUID referenceUUID;
    private double commissionFeeAmount;

    public CommissionFee(ReferenceUUID referenceUUID) {
        this.commissionFeeId = new CommissionFeeUUID(UUID.randomUUID());
        this.referenceUUID = referenceUUID;
        this.commissionFeeAmount = 0;
    }

    public void computeCommissionFee(double materialCosts) {
        this.commissionFeeAmount = materialCosts * 0.01;
    }
}
