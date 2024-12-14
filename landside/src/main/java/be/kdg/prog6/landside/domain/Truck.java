package be.kdg.prog6.landside.domain;

import be.kdg.prog6.landside.domain.uuid.TruckUUID;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Truck {

    private final TruckUUID truckUUID;
    private LicensePlate licensePlate;
    private TruckStatus truckStatus;

    public Truck(TruckUUID truckUUID, LicensePlate licensePlate, TruckStatus truckStatus) {
        this.truckUUID = truckUUID;
        this.licensePlate = licensePlate;
        this.truckStatus = truckStatus;
    }

    public Truck(LicensePlate licensePlate, TruckStatus truckStatus) {
        this.truckUUID = new TruckUUID(UUID.randomUUID());
        this.licensePlate = licensePlate;
        this.truckStatus = truckStatus;
    }

    public Truck(TruckUUID truckUUID) {
        this.truckUUID = truckUUID;
    }
}
