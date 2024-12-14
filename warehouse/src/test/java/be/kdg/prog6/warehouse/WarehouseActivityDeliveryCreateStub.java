package be.kdg.prog6.warehouse;

import be.kdg.prog6.warehouse.ports.out.WarehouseActivityDeliveryCreatePort;
import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.warehouse.domain.WarehouseActivity;

import java.util.UUID;

public class WarehouseActivityDeliveryCreateStub implements WarehouseActivityDeliveryCreatePort {
    private UUID warehouseActivityUUID;
    private WarehouseUUID warehouseUUID;
    private int warehouseNumber;
    private SellerUUID sellerUUID;
    private RawMaterialData rawMaterialData;
    private WarehouseActivity warehouseActivity;

    @Override
    public void createWarehouseActivity(UUID warehouseActivityUUID, WarehouseUUID warehouseUUID, int warehouseNumber, SellerUUID sellerUUID, RawMaterialData rawMaterialData, WarehouseActivity warehouseActivity) {
        this.warehouseActivityUUID = warehouseActivityUUID;
        this.warehouseUUID = warehouseUUID;
        this.warehouseNumber = warehouseNumber;
        this.sellerUUID = sellerUUID;
        this.rawMaterialData = rawMaterialData;
        this.warehouseActivity = warehouseActivity;
    }

    public RawMaterialData getRawMaterialData() {
        return rawMaterialData;
    }

    public WarehouseActivity getWarehouseActivity() {
        return warehouseActivity;
    }
}
