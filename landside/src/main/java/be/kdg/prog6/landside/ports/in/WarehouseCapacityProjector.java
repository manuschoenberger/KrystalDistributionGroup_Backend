package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.common.events.WarehouseActivityData;

import java.time.LocalDateTime;

public interface WarehouseCapacityProjector {

    void projectWarehouseCapacity(WarehouseUUID warehouseUUID, SellerUUID sellerUUID, int warehouseNumber, RawMaterialData rawMaterialData, WarehouseActivityData action, double amount, LocalDateTime time);
}
