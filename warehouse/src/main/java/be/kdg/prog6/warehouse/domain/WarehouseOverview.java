package be.kdg.prog6.warehouse.domain;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseOverview {
    private final WarehouseUUID warehouseUUID;
    private int warehouseNumber;
    private final RawMaterialData rawMaterialData;
    private final SellerUUID sellerUUID;
    private ActivityWindow activityWindow;
    private final double amount;

    public WarehouseOverview(WarehouseUUID warehouseUUID, int warehouseNumber, RawMaterialData rawMaterialData, SellerUUID sellerUUID, ActivityWindow activityWindow, double amount) {
        this.warehouseUUID = warehouseUUID;
        this.warehouseNumber = warehouseNumber;
        this.rawMaterialData = rawMaterialData;
        this.sellerUUID = sellerUUID;
        this.activityWindow = activityWindow;
        this.amount = amount;
    }
}

