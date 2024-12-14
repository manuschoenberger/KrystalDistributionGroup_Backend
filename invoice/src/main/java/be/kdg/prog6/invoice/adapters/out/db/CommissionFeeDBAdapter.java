package be.kdg.prog6.invoice.adapters.out.db;

import be.kdg.prog6.invoice.domain.CommissionFee;
import be.kdg.prog6.invoice.ports.out.CommissionFeeUpdatePort;
import org.springframework.stereotype.Repository;

@Repository
public class CommissionFeeDBAdapter implements CommissionFeeUpdatePort {
    private final CommissionFeeRepository commissionFeeRepository;

    public CommissionFeeDBAdapter(CommissionFeeRepository commissionFeeRepository) {
        this.commissionFeeRepository = commissionFeeRepository;
    }

    @Override
    public void updateCommissionFee(CommissionFee commissionFee) {
        CommissionFeeJpaEntity commissionFeeJpaEntity = new CommissionFeeJpaEntity(commissionFee.getCommissionFeeId().uuid());
        commissionFeeJpaEntity.setReferenceUUID(commissionFee.getReferenceUUID().uuid());
        commissionFeeJpaEntity.setCommissionFeeAmount(commissionFee.getCommissionFeeAmount());

        commissionFeeRepository.save(commissionFeeJpaEntity);
    }
}
