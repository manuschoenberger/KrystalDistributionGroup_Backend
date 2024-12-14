package be.kdg.prog6.warehouse.ports.in;

import be.kdg.prog6.common.domain.uuid.SellerUUID;

public record FetchWarehouseOverviewBySellerUUIDCommand(SellerUUID sellerUUID) {
}
