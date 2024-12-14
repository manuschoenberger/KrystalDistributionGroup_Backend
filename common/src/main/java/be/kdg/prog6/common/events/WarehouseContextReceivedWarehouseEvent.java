package be.kdg.prog6.common.events;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;

public record WarehouseContextReceivedWarehouseEvent(SellerUUID sellerUUID, WarehouseUUID warehouseUUID, RawMaterialData rawMaterialData) {
}
