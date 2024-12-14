package be.kdg.prog6.warehouse;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.warehouse.domain.ActivityWindow;
import be.kdg.prog6.warehouse.domain.Warehouse;
import be.kdg.prog6.warehouse.ports.out.WarehouseLoadPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseLoadPortStub implements WarehouseLoadPort {

    @Override
    public Optional<Warehouse> loadWarehouseBySellerAndRawMaterialData(SellerUUID sellerUUID, RawMaterialData rawMaterialData) {
        if (DataAndUUIDsStub.SELLER_UUID.equals(sellerUUID) && DataAndUUIDsStub.RAW_MATERIAL_DATA.equals(rawMaterialData)) {
            return Optional.of(
                    new Warehouse(
                            DataAndUUIDsStub.WAREHOUSE_UUID,
                            1,
                            DataAndUUIDsStub.RAW_MATERIAL_DATA,
                            new ActivityWindow(),
                            DataAndUUIDsStub.SELLER_UUID
                    )
            );
        }

        return Optional.empty();
    }

    @Override
    public Optional<Warehouse> loadWarehouseByWarehouseUUID(WarehouseUUID warehouseUUID) {
        if (DataAndUUIDsStub.WAREHOUSE_UUID.equals(warehouseUUID)) {
            return Optional.of(
                    new Warehouse(
                            DataAndUUIDsStub.WAREHOUSE_UUID,
                            1,
                            DataAndUUIDsStub.RAW_MATERIAL_DATA,
                            new ActivityWindow(),
                            DataAndUUIDsStub.SELLER_UUID
                    )
            );
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<Warehouse>> loadWarehousesBySellerUUID(SellerUUID sellerUUID) {
        if (DataAndUUIDsStub.SELLER_UUID.equals(sellerUUID)) {
            Warehouse warehouse = new Warehouse(
                    DataAndUUIDsStub.WAREHOUSE_UUID,
                    1,
                    DataAndUUIDsStub.RAW_MATERIAL_DATA,
                    new ActivityWindow(),
                    DataAndUUIDsStub.SELLER_UUID
            );

            List<Warehouse> warehouses = List.of(warehouse);

            return Optional.of(warehouses);
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<Warehouse>> loadAllWarehouses() {
        Warehouse warehouse = new Warehouse(
                DataAndUUIDsStub.WAREHOUSE_UUID,
                1,
                DataAndUUIDsStub.RAW_MATERIAL_DATA,
                new ActivityWindow(),
                DataAndUUIDsStub.SELLER_UUID
        );

        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);

        return Optional.of(warehouses);
    }
}


