package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.domain.TruckStatus;

public record WeighTruckCommand(LicensePlate licensePlate, TruckStatus truckStatus, double weight) {
}
