package be.kdg.prog6.warehouse.ports.in;

import be.kdg.prog6.warehouse.domain.WarehouseOverview;

import java.util.List;

public interface FetchingWarehouseOverviewUseCase {
    WarehouseOverview fetchWarehouseOverviewByWarehouseUUID(FetchWarehouseOverviewByWarehouseUUIDCommand fetchWarehouseOverviewByWarehouseUUIDCommand);
    List<WarehouseOverview> fetchWarehouseOverviewBySellerUUID(FetchWarehouseOverviewBySellerUUIDCommand fetchWarehouseOverviewBySellerUUIDCommand);

    List<WarehouseOverview> fetchWarehouseOverview();
}
