package be.kdg.prog6.invoice.core;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.invoice.domain.CommissionFee;
import be.kdg.prog6.invoice.ports.in.CommissionFeeCalculationUseCase;
import be.kdg.prog6.invoice.ports.out.CommissionFeeUpdatePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommissionFeeCalculationUseCase implements CommissionFeeCalculationUseCase {
    private final CommissionFeeUpdatePort commissionFeeUpdatePort;

    public DefaultCommissionFeeCalculationUseCase(CommissionFeeUpdatePort commissionFeeUpdatePort) {
        this.commissionFeeUpdatePort = commissionFeeUpdatePort;
    }

    @Override
    @Transactional
    public void computeCommissionFee(ReferenceUUID referenceUUID, double materialCosts) {
        CommissionFee commissionFee = new CommissionFee(referenceUUID);
        commissionFee.computeCommissionFee(materialCosts);

        commissionFeeUpdatePort.updateCommissionFee(commissionFee);
    }
}
