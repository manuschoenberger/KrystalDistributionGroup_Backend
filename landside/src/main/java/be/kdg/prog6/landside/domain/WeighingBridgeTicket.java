package be.kdg.prog6.landside.domain;

import be.kdg.prog6.landside.domain.uuid.WeighingBridgeTicketUUID;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class WeighingBridgeTicket {
    private final WeighingBridgeTicketUUID weighingBridgeTicketUUID;
    private LicensePlate licensePlate;
    private double grossWeight;
    private double tareWeight;
    private double netWeight;
    private LocalDateTime arrivalTimestamp;
    private LocalDateTime exitTimestamp;

    public WeighingBridgeTicket(LicensePlate licensePlate, double grossWeight, LocalDateTime arrivalTimestamp) {
        this.weighingBridgeTicketUUID = new WeighingBridgeTicketUUID(UUID.randomUUID());
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = 0;
        this.netWeight = 0;
        this.arrivalTimestamp = arrivalTimestamp;
    }

    public WeighingBridgeTicket(WeighingBridgeTicketUUID weighingBridgeTicketUUID) {
        this.weighingBridgeTicketUUID = weighingBridgeTicketUUID;
    }

    public void calculateNetWeight() {
        this.netWeight = this.grossWeight - this.tareWeight;
    }
}