package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.landside.domain.LicensePlate;

import java.util.UUID;

public record ScanLicensePlateCommand(LicensePlate licensePlate) {
}
