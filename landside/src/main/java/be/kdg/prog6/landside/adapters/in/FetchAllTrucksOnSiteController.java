package be.kdg.prog6.landside.adapters.in;

import be.kdg.prog6.landside.domain.Truck;
import be.kdg.prog6.landside.ports.in.FetchingAllTrucksOnSiteUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FetchAllTrucksOnSiteController {
    private final FetchingAllTrucksOnSiteUseCase fetchingAllTrucksOnSiteUseCase;

    public FetchAllTrucksOnSiteController(FetchingAllTrucksOnSiteUseCase fetchingAllTrucksOnSiteUseCase) {
        this.fetchingAllTrucksOnSiteUseCase = fetchingAllTrucksOnSiteUseCase;
    }

    @GetMapping("/site/trucks")
    public List<Truck> fetchAllTrucks() {
        return fetchingAllTrucksOnSiteUseCase.fetchAllTrucksOnSite();
    }
}
