package be.kdg.prog6.landside.ports.out;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.landside.domain.Warehouse;

import java.util.Optional;

public interface WarehouseLoadPort {

    Optional<Warehouse> loadWarehouseByWarehouseUUID(WarehouseUUID warehouseUUID, SellerUUID sellerUUID, int warehouseNumber, RawMaterialData rawMaterial);

    Optional<Warehouse> loadWarehouseBySellerIdAndPayloadData(SellerUUID sellerUUID, RawMaterialData rawMaterial);
}