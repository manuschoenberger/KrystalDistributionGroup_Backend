package be.kdg.prog6.landside.core;

import be.kdg.prog6.landside.domain.Truck;
import be.kdg.prog6.landside.domain.TruckStatus;
import be.kdg.prog6.landside.ports.in.FetchingAllTrucksOnSiteUseCase;
import be.kdg.prog6.landside.ports.out.TruckLoadPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultFetchingAllTrucksOnSiteUseCase implements FetchingAllTrucksOnSiteUseCase {
    private final TruckLoadPort truckLoadPort;

    public DefaultFetchingAllTrucksOnSiteUseCase(TruckLoadPort truckLoadPort) {
        this.truckLoadPort = truckLoadPort;
    }

    @Override
    @Transactional
    public List<Truck> fetchAllTrucksOnSite() {
        Optional<List<Truck>> optionalTrucks = truckLoadPort.loadAllTrucks();
        List<Truck> trucks = optionalTrucks.orElseGet(List::of);

        return trucks.stream()
                .filter(truck -> !TruckStatus.NOT_ON_SITE.equals(truck.getTruckStatus()))
                .collect(Collectors.toList());
    }
}
