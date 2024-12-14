package be.kdg.prog6.common.events;


import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;

import java.time.LocalDateTime;

public record WarehouseActivityCreatedEvent(WarehouseUUID warehouseUUID, SellerUUID sellerUUID, int warehouseNumber, RawMaterialData rawMaterialData, WarehouseActivityData warehouseActivityData, double amount, LocalDateTime time) {
}
