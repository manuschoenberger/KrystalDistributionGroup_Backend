package be.kdg.prog6.warehouse.ports.out;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.warehouse.domain.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseLoadPort {
    Optional<Warehouse> loadWarehouseBySellerAndRawMaterialData(SellerUUID sellerUUID, RawMaterialData rawMaterialData);

    Optional<Warehouse> loadWarehouseByWarehouseUUID(WarehouseUUID warehouseUUID);

    Optional<List<Warehouse>> loadWarehousesBySellerUUID(SellerUUID sellerUUID);

    Optional<List<Warehouse>> loadAllWarehouses();
}
