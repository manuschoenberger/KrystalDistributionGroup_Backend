package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.domain.Truck;
import be.kdg.prog6.landside.domain.uuid.TruckUUID;
import be.kdg.prog6.landside.ports.out.TruckLoadPort;
import be.kdg.prog6.landside.ports.out.TruckUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TruckDBAdapter implements TruckLoadPort, TruckUpdatePort {
    private final TruckRepository truckRepository;

    public TruckDBAdapter(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    public Optional<Truck> loadTruckByLicensePlate(LicensePlate licensePlate) {
        Optional<TruckJpaEntity> truckJpaEntityOptional = truckRepository.findByLicensePlate(licensePlate.licensePlate());

        if (truckJpaEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        TruckJpaEntity truckJpaEntity = truckJpaEntityOptional.get();

        Truck truck = new Truck(new TruckUUID(truckJpaEntity.getTruckUUID()));
        truck.setLicensePlate(new LicensePlate(truckJpaEntity.getLicensePlate()));
        truck.setTruckStatus(truckJpaEntity.getTruckStatus());

        return Optional.of(truck);
    }

    @Override
    public Optional<List<Truck>> loadAllTrucks() {
        List<TruckJpaEntity> truckJpaEntities = truckRepository.findAll();

        if (truckJpaEntities.isEmpty()) {
            return Optional.empty();
        }

        List<Truck> trucks = new ArrayList<>();

        for (TruckJpaEntity truckJpaEntity : truckJpaEntities) {
            Truck truck = new Truck(new TruckUUID(truckJpaEntity.getTruckUUID()));
            truck.setLicensePlate(new LicensePlate(truckJpaEntity.getLicensePlate()));
            truck.setTruckStatus(truckJpaEntity.getTruckStatus());

            trucks.add(truck);
        }

        return Optional.of(trucks);
    }

    @Override
    public void updateTruck(Truck truck) {
        TruckJpaEntity truckJpaEntity = new TruckJpaEntity(truck.getTruckUUID().uuid());
        truckJpaEntity.setLicensePlate(truck.getLicensePlate().licensePlate());
        truckJpaEntity.setTruckStatus(truck.getTruckStatus());

        truckRepository.save(truckJpaEntity);
    }
}
