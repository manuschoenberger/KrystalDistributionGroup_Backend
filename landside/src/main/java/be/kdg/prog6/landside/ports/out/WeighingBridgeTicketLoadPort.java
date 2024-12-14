package be.kdg.prog6.landside.ports.out;

import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.domain.WeighingBridgeTicket;

import java.util.Optional;

public interface WeighingBridgeTicketLoadPort {
    Optional<WeighingBridgeTicket> loadWeighingBridgeTicketByLicensePlate(LicensePlate licensePlate);
}
