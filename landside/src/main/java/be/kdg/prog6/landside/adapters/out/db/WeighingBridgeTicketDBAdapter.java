package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.domain.WeighingBridgeTicket;
import be.kdg.prog6.landside.domain.uuid.WeighingBridgeTicketUUID;
import be.kdg.prog6.landside.ports.out.WeighingBridgeTicketLoadPort;
import be.kdg.prog6.landside.ports.out.WeighingBridgeTicketUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WeighingBridgeTicketDBAdapter implements WeighingBridgeTicketLoadPort, WeighingBridgeTicketUpdatePort {

    private final WeighingBridgeTicketRepository weighingBridgeTicketRepository;

    public WeighingBridgeTicketDBAdapter(WeighingBridgeTicketRepository weighingBridgeTicketRepository) {
        this.weighingBridgeTicketRepository = weighingBridgeTicketRepository;
    }

    @Override
    public Optional<WeighingBridgeTicket> loadWeighingBridgeTicketByLicensePlate(LicensePlate licensePlate) {
        Optional<WeighingBridgeTicketJpaEntity> weighingBridgeTicketJpaEntity =
                weighingBridgeTicketRepository.findByLicensePlate(licensePlate.licensePlate());

        if (weighingBridgeTicketJpaEntity.isEmpty()) {
            return Optional.empty();
        }

        WeighingBridgeTicket weighingBridgeTicket = new WeighingBridgeTicket(
                new WeighingBridgeTicketUUID(weighingBridgeTicketJpaEntity.get().getWeighingBridgeTicketUUID())
        );

        weighingBridgeTicket.setLicensePlate(licensePlate);
        weighingBridgeTicket.setGrossWeight(weighingBridgeTicketJpaEntity.get().getGrossWeight());
        weighingBridgeTicket.setTareWeight(weighingBridgeTicketJpaEntity.get().getTareWeight());
        weighingBridgeTicket.setNetWeight(weighingBridgeTicketJpaEntity.get().getNetWeight());
        weighingBridgeTicket.setArrivalTimestamp(weighingBridgeTicketJpaEntity.get().getArrivalTimestamp());
        weighingBridgeTicket.setExitTimestamp(weighingBridgeTicketJpaEntity.get().getExitTimestamp());

        return Optional.of(weighingBridgeTicket);
    }

    @Override
    public void updateWeighingBridgeTicket(WeighingBridgeTicket weighingBridgeTicket) {
        WeighingBridgeTicketJpaEntity weighingBridgeTicketJpaEntity =
                new WeighingBridgeTicketJpaEntity(weighingBridgeTicket.getWeighingBridgeTicketUUID().uuid());

        weighingBridgeTicketJpaEntity.setLicensePlate(weighingBridgeTicket.getLicensePlate().licensePlate());
        weighingBridgeTicketJpaEntity.setGrossWeight(weighingBridgeTicket.getGrossWeight());
        weighingBridgeTicketJpaEntity.setTareWeight(weighingBridgeTicket.getTareWeight());
        weighingBridgeTicketJpaEntity.setNetWeight(weighingBridgeTicket.getNetWeight());
        weighingBridgeTicketJpaEntity.setArrivalTimestamp(weighingBridgeTicket.getArrivalTimestamp());
        weighingBridgeTicketJpaEntity.setExitTimestamp(weighingBridgeTicket.getExitTimestamp());

        weighingBridgeTicketRepository.save(weighingBridgeTicketJpaEntity);
    }
}
