package be.kdg.prog6.warehouse.ports.out;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.warehouse.domain.WarehouseActivity;

import java.util.UUID;

public interface WarehouseActivityDeliveryCreatePort {
    void createWarehouseActivity(UUID warehouseActivityUUID, WarehouseUUID warehouseUUID, int warehouseNumber, SellerUUID sellerUUID, RawMaterialData rawMaterialData, WarehouseActivity warehouseActivity);
}
