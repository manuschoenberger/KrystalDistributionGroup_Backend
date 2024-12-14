package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.landside.domain.Truck;

import java.util.List;

public interface FetchingAllTrucksOnSiteUseCase {
    List<Truck> fetchAllTrucksOnSite();
}
