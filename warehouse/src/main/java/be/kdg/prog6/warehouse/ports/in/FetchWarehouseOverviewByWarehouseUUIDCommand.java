package be.kdg.prog6.warehouse.ports.in;

import be.kdg.prog6.common.domain.uuid.WarehouseUUID;

public record FetchWarehouseOverviewByWarehouseUUIDCommand(WarehouseUUID warehouseUUID) {
}
