package be.kdg.prog6.invoice.ports.out;

import be.kdg.prog6.invoice.domain.CommissionFee;

public interface CommissionFeeUpdatePort {
    void updateCommissionFee(CommissionFee commissionFee);
}
