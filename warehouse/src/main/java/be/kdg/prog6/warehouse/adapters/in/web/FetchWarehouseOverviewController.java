package be.kdg.prog6.warehouse.adapters.in.web;

import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.warehouse.domain.WarehouseOverview;
import be.kdg.prog6.warehouse.ports.in.FetchWarehouseOverviewBySellerUUIDCommand;
import be.kdg.prog6.warehouse.ports.in.FetchWarehouseOverviewByWarehouseUUIDCommand;
import be.kdg.prog6.warehouse.ports.in.FetchingWarehouseOverviewUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/warehouse")
public class FetchWarehouseOverviewController {
    private final FetchingWarehouseOverviewUseCase fetchingWarehouseOverviewUseCase;

    public FetchWarehouseOverviewController(FetchingWarehouseOverviewUseCase fetchingWarehouseOverviewUseCase) {
        this.fetchingWarehouseOverviewUseCase = fetchingWarehouseOverviewUseCase;
    }

    @GetMapping()
    public List<WarehouseOverview> fetchWarehouseOverview() {
        return fetchingWarehouseOverviewUseCase.fetchWarehouseOverview();
    }

    @GetMapping("/{warehouseUUID}")
    public WarehouseOverview fetchWarehouseOverviewByWarehouseUUID(@PathVariable String warehouseUUID) {

        return fetchingWarehouseOverviewUseCase.fetchWarehouseOverviewByWarehouseUUID(
                new FetchWarehouseOverviewByWarehouseUUIDCommand(
                        new WarehouseUUID(UUID.fromString(warehouseUUID))
                )
        );
    }

    @GetMapping("/seller/{sellerUUID}")
    public List<WarehouseOverview> fetchWarehouseOverviewBySellerUUID(@PathVariable String sellerUUID) {

        return fetchingWarehouseOverviewUseCase.fetchWarehouseOverviewBySellerUUID(
                new FetchWarehouseOverviewBySellerUUIDCommand(
                        new SellerUUID(UUID.fromString(sellerUUID))
                )
        );
    }
}
