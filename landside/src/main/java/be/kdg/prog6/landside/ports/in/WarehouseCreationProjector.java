package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;

public interface WarehouseCreationProjector {
    void projectWarehouseCreation(WarehouseUUID warehouseUUID, SellerUUID sellerUUID, RawMaterialData rawMaterialData);

}
