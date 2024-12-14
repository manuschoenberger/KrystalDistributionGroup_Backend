package be.kdg.prog6.landside.ports.out;

import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.domain.Truck;

import java.util.List;
import java.util.Optional;

public interface TruckLoadPort {
    Optional<Truck> loadTruckByLicensePlate(LicensePlate licensePlate);

    Optional<List<Truck>> loadAllTrucks();
}
