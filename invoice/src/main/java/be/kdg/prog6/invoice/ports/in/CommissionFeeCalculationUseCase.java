package be.kdg.prog6.invoice.ports.in;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;

public interface CommissionFeeCalculationUseCase {
    void computeCommissionFee(ReferenceUUID referenceUUID, double materialCosts);
}
