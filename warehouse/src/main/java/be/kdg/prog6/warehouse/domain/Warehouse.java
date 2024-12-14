package be.kdg.prog6.warehouse.domain;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.warehouse.domain.uuid.WarehouseActivityUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.common.events.WarehouseActivityData;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Warehouse {
    private static final double CAPACITY = 500000;

    private static int warehouseNumberCounter = 0;
    private final WarehouseUUID warehouseUUID;
    private int warehouseNumber;
    private final RawMaterialData rawMaterialData;
    private final SellerUUID sellerUUID;
    private ActivityWindow activityWindow;

    public Warehouse(RawMaterialData rawMaterialData, SellerUUID sellerUUID) {
        this.warehouseUUID = new WarehouseUUID(UUID.randomUUID());
        this.warehouseNumber = getNextWarehouseNumber();
        this.rawMaterialData = rawMaterialData;
        this.activityWindow = new ActivityWindow();
        this.sellerUUID = sellerUUID;
    }

    public Warehouse(WarehouseUUID warehouseUUID, int warehouseNumber, RawMaterialData rawMaterialData, ActivityWindow activityWindow, SellerUUID sellerUUID) {
        this.warehouseUUID = warehouseUUID;
        this.warehouseNumber = warehouseNumber;
        this.rawMaterialData = rawMaterialData;
        this.activityWindow = activityWindow;
        this.sellerUUID = sellerUUID;
    }

    public WarehouseActivity deliverRawMaterial(double amount, RawMaterialData rawMaterialData) {
        WarehouseActivity warehouseActivity = new WarehouseActivity(
                new WarehouseActivityUUID(UUID.randomUUID()),
                WarehouseActivityData.DELIVERY,
                amount,
                LocalDateTime.now(),
                0,
                rawMaterialData
        );

        activityWindow.add(warehouseActivity);

        return warehouseActivity;
    }

    public WarehouseActivity shipRawMaterial(double amount, RawMaterialData rawMaterialData) {
        WarehouseActivity warehouseActivity = new WarehouseActivity(
                new WarehouseActivityUUID(UUID.randomUUID()),
                WarehouseActivityData.SHIPMENT,
                amount,
                LocalDateTime.now(),
                0,
                rawMaterialData
        );

        activityWindow.add(warehouseActivity);

        return warehouseActivity;
    }

    private int getNextWarehouseNumber() {
        return warehouseNumberCounter++;
    }

    public double computeCapacity() {
        return activityWindow.computeCapacity();
    }

    public void addWarehouseActivity(be.kdg.prog6.warehouse.domain.WarehouseActivity warehouseActivity) {
        activityWindow.add(warehouseActivity);
    }
}
